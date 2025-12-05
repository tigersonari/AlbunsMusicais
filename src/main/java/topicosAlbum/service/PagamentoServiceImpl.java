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

    // ---------------- BUSCAR PAGAMENTO ----------------

    @Override
    public PagamentoResponseDTO findById(Long idPagamento) {

        Pagamento p = pagamentoRepository.findById(idPagamento);
        if (p == null)
            throw ValidationException.of("id", "Pagamento não encontrado.");

        return toDTO(p);
    }

    // ---------------- GERAR PIX PARA UM PEDIDO ----------------

    @Override
    @Transactional
    public PagamentoResponseDTO gerarPixParaPedido(Long idPedido) {

        Pedido pedido = pedidoRepository.findById(idPedido);
        if (pedido == null)
            throw ValidationException.of("pedido", "Pedido não encontrado.");

        Pagamento pagamento = pedido.getPagamento();

        if (pagamento == null)
            throw ValidationException.of("pagamento", "Pedido não possui pagamento.");

        if (!"PIX".equalsIgnoreCase(pagamento.getMetodoPagamento()))
            throw ValidationException.of("metodo", "Pagamento não é do tipo PIX.");

        if (pagamento.getCodigoPagamento() != null)
            throw ValidationException.of("pix", "PIX já foi gerado para este pedido.");

        // Geração de dados PIX (SIMULADO)
        String txid = UUID.randomUUID().toString().replace("-", "");
        BigDecimal valor = pagamento.getValor();

        String copiaCola = gerarCodigoCopiaCola(txid, valor, pedido.getId());

        String qrCodeBase64 = gerarQrCodeFakeBase64(copiaCola);

        pagamento.setCodigoPagamento(copiaCola);
        pagamento.setStatus("PENDENTE");

        // Se você quiser evoluir depois, pode salvar também:
        // - txid
        // - payload completo

        pagamentoRepository.persist(pagamento);

        return new PagamentoResponseDTO(
            pagamento.getId(),
            pagamento.getMetodoPagamento(),
            pagamento.getStatus(),
            pagamento.getValor(),
            qrCodeBase64,
            pagamento.getDataCriacao()
        );
    }

    // ---------------- CONFIRMAR PAGAMENTO ----------------

    @Override
    @Transactional
    public void confirmarPagamento(Long idPagamento) {

        Pagamento pagamento = pagamentoRepository.findById(idPagamento);
        if (pagamento == null)
            throw ValidationException.of("id", "Pagamento não encontrado.");

        if ("APROVADO".equalsIgnoreCase(pagamento.getStatus()))
            throw ValidationException.of("status", "Pagamento já está aprovado.");

        pagamento.setStatus("APROVADO");

        Pedido pedido = pagamento.getPedido();
        pedido.setStatus("PAGO");
    }

    // ---------------- AUXILIARES PRIVADOS ----------------

    private PagamentoResponseDTO toDTO(Pagamento p) {
        return new PagamentoResponseDTO(
            p.getId(),
            p.getMetodoPagamento(),
            p.getStatus(),
            p.getValor(),
            p.getCodigoPagamento(),
            p.getDataCriacao()
        );
    }

    /**
     * Gera um código PIX "copia e cola" SIMULADO.
     */
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

    /**
     * Simula um QR Code em BASE64 a partir do código PIX.
     * (Não é um QR real, mas serve perfeitamente para o trabalho e para testes)
     */
    private String gerarQrCodeFakeBase64(String copiaCola) {
        return Base64.getEncoder().encodeToString(copiaCola.getBytes());
    }
}
