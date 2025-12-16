package topicosAlbum.service;

import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import topicosAlbum.dto.EnderecoResponseDTO;
import topicosAlbum.dto.ItemPedidoDTO;
import topicosAlbum.dto.ItemPedidoResponseDTO;
import topicosAlbum.dto.PagamentoDTO;
import topicosAlbum.dto.PagamentoResponseDTO;
import topicosAlbum.dto.PedidoDTO;
import topicosAlbum.dto.PedidoResponseDTO;
import topicosAlbum.exception.ValidationException;
import topicosAlbum.model.CartaoSalvo;
import topicosAlbum.model.Endereco;
import topicosAlbum.model.ItemPedido;
import topicosAlbum.model.Pagamento;
import topicosAlbum.model.Pedido;
import topicosAlbum.model.Produto;
import topicosAlbum.model.Usuario;
import topicosAlbum.repository.CartaoSalvoRepository;
import topicosAlbum.repository.EnderecoRepository;
import topicosAlbum.repository.PagamentoRepository;
import topicosAlbum.repository.PedidoRepository;
import topicosAlbum.repository.ProdutoRepository;
import topicosAlbum.repository.UsuarioRepository;

@ApplicationScoped
public class PedidoServiceImpl implements PedidoService {

    @Inject PedidoRepository pedidoRepository;
    @Inject ProdutoRepository produtoRepository;
    @Inject UsuarioRepository usuarioRepository;
    @Inject EnderecoRepository enderecoRepository;
    @Inject PagamentoRepository pagamentoRepository;
    @Inject CartaoSalvoRepository cartaoSalvoRepository;

    @Inject EstoqueService estoqueService;
    @Inject ItemPedidoService itemPedidoService;

    
    //               CRIAR PEDIDO COM REGRA DE USUÁRIO LOGADO
    @Transactional
    public PedidoResponseDTO createParaUsuario(PedidoDTO dto, Long idUsuarioToken) {

        Usuario usuario = usuarioRepository.findById(idUsuarioToken);
        if (usuario == null)
            throw ValidationException.of("usuario", "Usuário autenticado não encontrado.");

        // ENDEREÇO
        Endereco endereco = null;

        // Se NÃO enviaram idEnderecoEntrega e também NÃO enviaram enderecoNovo = erro
        if (dto.idEnderecoEntrega() == null && dto.endereco() == null) {
            throw ValidationException.of("endereco", 
                    "É necessário informar um idEnderecoEntrega OU um novo endereço.");
        }

        // Se enviaram AMBOS = conflito
        if (dto.idEnderecoEntrega() != null && dto.endereco() != null) {
            throw ValidationException.of("endereco", 
                    "Não é permitido informar idEnderecoEntrega e um novo endereço ao mesmo tempo.");
        }

        // Se enviaram idEnderecoEntrega = buscar endereço existente
        if (dto.idEnderecoEntrega() != null) {
            endereco = enderecoRepository.findById(dto.idEnderecoEntrega());
        
            if (endereco == null)
                throw ValidationException.of("endereco", "Endereço não encontrado.");
        
            if (!endereco.getUsuario().getId().equals(idUsuarioToken))
                throw ValidationException.of("endereco", 
                        "O endereço informado não pertence ao usuário autenticado.");
        }

        //  Se não enviaram idEnderecoEntrega = criar novo endereço
        else {
            Endereco novo = new Endereco();
            novo.setUsuario(usuario);
            novo.setRua(dto.endereco().rua());
            novo.setNumero(dto.endereco().numero());
            novo.setComplemento(dto.endereco().complemento());
            novo.setBairro(dto.endereco().bairro());
            novo.setCidade(dto.endereco().cidade());
            novo.setUf(dto.endereco().uf());
            novo.setCep(dto.endereco().cep());
        
            enderecoRepository.persist(novo);
            endereco = novo;
        }


        // CRIA PEDIDO
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setEnderecoEntrega(endereco);
        pedido.setObservacao(dto.observacao());
        pedido.setStatus("PAGAMENTO_PENDENTE");

        pedidoRepository.persist(pedido);

        // ITENS
        for (ItemPedidoDTO itemDTO : dto.itens()) {

            Produto produto = produtoRepository.findById(itemDTO.idProduto());
            if (produto == null)
                throw ValidationException.of("produto", "Produto não encontrado.");

            estoqueService.verificarDisponibilidade(produto.getId(), itemDTO.quantidade());

            /*ItemPedido item = new ItemPedido();
            item.setPedido(pedido);
            item.setProduto(produto);
            item.setQuantidade(itemDTO.quantidade());
            item.setPrecoUnitario(produto.getPreco());

            itemPedidoService.salvar(item);*/

            ItemPedido item = new ItemPedido();
            item.setProduto(produto);
            item.setQuantidade(itemDTO.quantidade());
            item.setPrecoUnitario(produto.getPreco());

            // ESSENCIAL: adicionar item dentro do pedido
            pedido.addItem(item);

            // agora salva
            itemPedidoService.salvar(item);

        }

        pedido.recalcTotal();

        for (ItemPedido item : pedido.getItens()) {
            estoqueService.baixarEstoque(item.getProduto().getId(), item.getQuantidade());
        }

        // PAGAMENTO
        Pagamento pagamento = processarPagamento(dto.pagamento(), pedido, usuario, idUsuarioToken);

        pagamentoRepository.persist(pagamento);
        pedido.setPagamento(pagamento);

        return toResponseDTO(pedido);
    }

    //  PROCESSAMENTO DE PAGAMENTO
    private Pagamento processarPagamento(
            PagamentoDTO dto,
            Pedido pedido,
            Usuario usuario,
            Long idUsuarioToken
    ) {
        Pagamento pagamento = new Pagamento();

        pagamento.setPedido(pedido);
        pagamento.setMetodoPagamento(dto.metodoPagamento().toUpperCase());
        pagamento.setStatus("PENDENTE");
        pagamento.setValor(pedido.getTotal());

        String metodo = pagamento.getMetodoPagamento();

        switch (metodo) {

            case "PIX" -> {
                pagamento.setCodigoPagamento(null);
            }

            case "BOLETO" -> {
                String linha = "34191.79001 01043.510047 91020.150008 1 000000" + pedido.getId();
                String pdfBase64 = Base64.getEncoder().encodeToString(("BOLETO-" + pedido.getId()).getBytes());
                pagamento.setCodigoPagamento(linha + "|PDF:" + pdfBase64);
            }

            case "CARTAO" -> {
                // Cartão salvo
                if (dto.idCartaoSalvo() != null) {

                    CartaoSalvo cartao = cartaoSalvoRepository.buscarDoUsuario(dto.idCartaoSalvo(), idUsuarioToken);

                    if (cartao == null)
                        throw ValidationException.of("cartao", "Cartão salvo não encontrado ou não pertence ao usuário.");

                    pagamento.setCodigoPagamento("TRANS-" + UUID.randomUUID());
                    pagamento.setUltimos4(cartao.getUltimos4());
                    pagamento.setBandeira(cartao.getBandeira());
                }

                // Novo cartão
                else if (dto.numeroCartao() != null) {

                    String numero = dto.numeroCartao().replace(" ", "");

                    if (numero.length() < 12)
                        throw ValidationException.of("cartao", "Número do cartão inválido.");

                    CartaoSalvo novo = new CartaoSalvo();
                    novo.setUsuario(usuario);
                    novo.setNomeTitular(dto.nomeImpresso());
                    novo.setNumeroMascarado("**** **** **** " + numero.substring(numero.length() - 4));
                    novo.setUltimos4(numero.substring(numero.length() - 4));
                    novo.setValidade(dto.validade());
                    novo.setBandeira(detectarBandeira(numero));

                    cartaoSalvoRepository.persist(novo);

                    pagamento.setCodigoPagamento("TRANS-" + UUID.randomUUID());
                    pagamento.setUltimos4(novo.getUltimos4());
                    pagamento.setBandeira(novo.getBandeira());
                }

                else {
                    throw ValidationException.of("cartao", "Informe idCartaoSalvo OU dados de um novo cartão.");
                }
            }

            default -> {
                throw ValidationException.of("metodo", "Método de pagamento inválido.");
            }
        }

        return pagamento;
    }

    // Detecta bandeira (simplificado)
    private String detectarBandeira(String numero) {

        if (numero.startsWith("4"))
            return "VISA";

        if (numero.startsWith("5"))
            return "MASTERCARD";

        if (numero.startsWith("34") || numero.startsWith("37"))
            return "AMEX";

        return "DESCONHECIDA";
    }

    //   BUSCAS / CANCELAMENTO

    @Override
    public PedidoResponseDTO findById(Long idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido);
        if (pedido == null)
            throw ValidationException.of("id", "Pedido não encontrado.");
        return toResponseDTO(pedido);
    }

    @Override
    public PedidoResponseDTO findByIdSeguro(Long idPedido, Long idUsuarioToken, boolean isAdmin) {

        Pedido pedido = pedidoRepository.findById(idPedido);

        if (pedido == null)
            throw ValidationException.of("id", "Pedido não encontrado.");

        if (!isAdmin && !pedido.getUsuario().getId().equals(idUsuarioToken))
            throw ValidationException.of("acesso", "Você não pode ver pedidos de outro usuário.");

        return toResponseDTO(pedido);
    }

    @Override
    public List<PedidoResponseDTO> findByUsuario(Long idUsuario) {
        return pedidoRepository.findByUsuario(idUsuario)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void cancelar(Long idPedido) {

        Pedido pedido = pedidoRepository.findById(idPedido);

        if (pedido == null)
            throw ValidationException.of("id", "Pedido não encontrado.");

        if ("PAGO".equalsIgnoreCase(pedido.getStatus()))
            throw ValidationException.of("status", "Pedido já foi pago.");

        pedido.setStatus("CANCELADO");
        pedido.getPagamento().setStatus("REJEITADO");

        for (ItemPedido item : pedido.getItens()) {
            estoqueService.reporEstoque(item.getProduto().getId(), item.getQuantidade());
        }
    }

    @Override
    @Transactional
    public void cancelarSeguro(Long idPedido, Long idUsuarioToken) {

        Pedido pedido = pedidoRepository.findById(idPedido);

        if (pedido == null)
            throw ValidationException.of("id", "Pedido não encontrado.");

        if (!pedido.getUsuario().getId().equals(idUsuarioToken))
            throw ValidationException.of("acesso", "Você não pode cancelar pedidos de outro usuário.");

        cancelar(idPedido);
    }

    //    DTO DE RESPOSTA
    private PedidoResponseDTO toResponseDTO(Pedido pedido) {

        List<ItemPedidoResponseDTO> itensDTO = pedido.getItens()
                .stream()
                .map(item -> new ItemPedidoResponseDTO(
                        item.getProduto().getId(),
                        item.getProduto().getAlbum().getTitulo(),
                        item.getQuantidade(),
                        item.getPrecoUnitario(),
                        item.getSubtotal()
                ))
                .collect(Collectors.toList());

        Pagamento p = pedido.getPagamento();

        PagamentoResponseDTO pagamentoDTO =
                new PagamentoResponseDTO(
                        p.getId(),
                        p.getMetodoPagamento(),
                        p.getStatus(),
                        p.getValor(),
                        p.getCodigoPagamento(),
                        p.getDataCriacao(),
                        p.getUltimos4(),
                        p.getBandeira()
                );

        EnderecoResponseDTO enderecoDTO = pedido.getEnderecoEntrega() != null ? 
            new EnderecoResponseDTO(
                pedido.getEnderecoEntrega().getId(),
                pedido.getEnderecoEntrega().getRua(),
                pedido.getEnderecoEntrega().getNumero(),
                pedido.getEnderecoEntrega().getComplemento(),
                pedido.getEnderecoEntrega().getBairro(),
                pedido.getEnderecoEntrega().getCidade(),
                pedido.getEnderecoEntrega().getUf(),
                pedido.getEnderecoEntrega().getCep()
            ) : null;


        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getDataCriacao(),
                pedido.getTotal(),
                pedido.getStatus(),
                pedido.getObservacao(),
                /*pedido.getUsuario().getId(),*/
                /*pedido.getEnderecoEntrega() != null ? pedido.getEnderecoEntrega().getId() : null,*/
                enderecoDTO,
                itensDTO,
                pagamentoDTO
        );
    }

}
