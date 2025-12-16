package topicosAlbum.service;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import topicosAlbum.dto.PagamentoResponseDTO;
import topicosAlbum.exception.ValidationException;
import topicosAlbum.model.Pagamento;
import topicosAlbum.model.Pedido;
import topicosAlbum.repository.PagamentoRepository;
import topicosAlbum.repository.PedidoRepository;

@ApplicationScoped
public class PagamentoServiceImpl implements PagamentoService {

    @Inject
    PagamentoRepository pagamentoRepository;

    @Inject
    PedidoRepository pedidoRepository;

    @Inject
    PaymentGatewayFake gateway;

    // ---------------- BUSCAR PAGAMENTO (SEGURO) ----------------

    @Override
    public PagamentoResponseDTO findByIdSeguro(Long idPagamento, Long idUsuarioToken, boolean isAdmin) {

        Pagamento p = pagamentoRepository.findById(idPagamento);
        if (p == null)
            throw ValidationException.of("id", "Pagamento não encontrado.");

        if (!isAdmin && !p.getPedido().getUsuario().getId().equals(idUsuarioToken))
            throw ValidationException.of("acesso", "Você não pode acessar o pagamento de outro usuário.");

        return toDTO(p);
    }

    // ---------------- GERAR PIX (SEGURO) ----------------

    @Override
    @Transactional
    public PagamentoResponseDTO gerarPixParaPedidoSeguro(Long idPedido, Long idUsuarioToken, boolean isAdmin) {

        Pedido pedido = pedidoRepository.findById(idPedido);
        if (pedido == null)
            throw ValidationException.of("pedido", "Pedido não encontrado.");

        if (!isAdmin && !pedido.getUsuario().getId().equals(idUsuarioToken))
            throw ValidationException.of("acesso", "Você não pode gerar PIX para pedido de outro usuário.");

        Pagamento pagamento = pedido.getPagamento();

        if (!"PIX".equalsIgnoreCase(pagamento.getMetodoPagamento()))
            throw ValidationException.of("metodo", "Pagamento não é do tipo PIX.");

        if (pagamento.getCodigoPagamento() != null)
            throw ValidationException.of("pix", "PIX já foi gerado.");

        // gera copia e cola
        String txid = UUID.randomUUID().toString().replace("-", "");
        BigDecimal valor = pagamento.getValor();

        String copiaCola = gerarCodigoCopiaCola(txid, valor, pedido.getId());
        String qr = gerarQrCodeFakeBase64(copiaCola);

        pagamento.setCodigoPagamento(copiaCola);
        pagamento.setStatus("PENDENTE");

        return new PagamentoResponseDTO(
                pagamento.getId(),
                pagamento.getMetodoPagamento(),
                pagamento.getStatus(),
                pagamento.getValor(),
                copiaCola,
                pagamento.getDataCriacao(),
                pagamento.getUltimos4(),
                pagamento.getBandeira()
        );
    }

    // ---------------- SOLICITAR CONFIRMAÇÃO (não aprova aqui!) ----------------

    @Override
    @Transactional
    public void solicitarConfirmacao(Long idPagamento, Long idUsuarioToken) {

        Pagamento p = pagamentoRepository.findById(idPagamento);
        if (p == null)
            throw ValidationException.of("id", "Pagamento não encontrado.");

        Long dono = p.getPedido().getUsuario().getId();

        if (!dono.equals(idUsuarioToken))
            throw ValidationException.of("acesso", "Você não pode confirmar pagamento de outro usuário.");

        if ("APROVADO".equalsIgnoreCase(p.getStatus()))
            throw ValidationException.of("status", "Pagamento já está aprovado.");

        if ("REJEITADO".equalsIgnoreCase(p.getStatus()))
            throw ValidationException.of("status", "Pagamento já foi rejeitado.");

        // NÃO CONFIRMA AQUI!!
        // agendado automaticamente pelo scheduler
        p.setStatus("PENDENTE");
    }

    // ---------------- AUXILIARES PRIVADOS ----------------

    private PagamentoResponseDTO toDTO(Pagamento p) {
        return new PagamentoResponseDTO(
                p.getId(),
                p.getMetodoPagamento(),
                p.getStatus(),
                p.getValor(),
                p.getCodigoPagamento(),
                p.getDataCriacao(),
                p.getUltimos4(),
                p.getBandeira()
        );
    }

    private String gerarCodigoCopiaCola(String txid, BigDecimal valor, Long idPedido) {
        return "00020126360014BR.GOV.BCB.PIX"
             + "01" + String.format("%02d", txid.length()) + txid
             + "52040000"
             + "5303986"
             + "54" + String.format("%02d", valor.toPlainString().length()) + valor
             + "5802BR"
             + "6009SAO PAULO"
             + "62070503***"
             + "6304ABCD"
             + "-PEDIDO-" + idPedido;
    }

    private String gerarQrCodeFakeBase64(String copiaCola) {
        return Base64.getEncoder().encodeToString(copiaCola.getBytes());
    }
}
