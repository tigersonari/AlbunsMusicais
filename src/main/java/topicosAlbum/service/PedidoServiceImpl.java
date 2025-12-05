package topicosAlbum.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import topicosAlbum.dto.ItemPedidoDTO;
import topicosAlbum.dto.ItemPedidoResponseDTO;
import topicosAlbum.dto.PagamentoResponseDTO;
import topicosAlbum.dto.PedidoDTO;
import topicosAlbum.dto.PedidoResponseDTO;
import topicosAlbum.exception.ValidationException;
import topicosAlbum.model.Endereco;
import topicosAlbum.model.ItemPedido;
import topicosAlbum.model.Pagamento;
import topicosAlbum.model.Pedido;
import topicosAlbum.model.Produto;
import topicosAlbum.model.Usuario;
import topicosAlbum.repository.EnderecoRepository;
import topicosAlbum.repository.PagamentoRepository;
import topicosAlbum.repository.PedidoRepository;
import topicosAlbum.repository.ProdutoRepository;
import topicosAlbum.repository.UsuarioRepository;

@ApplicationScoped
public class PedidoServiceImpl implements PedidoService {

    @Inject
    PedidoRepository pedidoRepository;

    @Inject
    ProdutoRepository produtoRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    EnderecoRepository enderecoRepository;

    @Inject
    PagamentoRepository pagamentoRepository;

    @Inject
    EstoqueService estoqueService;

    @Inject
    ItemPedidoService itemPedidoService;

    // -------- CRIAR PEDIDO --------

    @Override
    @Transactional
    public PedidoResponseDTO create( PedidoDTO dto) {

        Usuario usuario = usuarioRepository.findById(dto.idUsuario());
        if (usuario == null)
            throw ValidationException.of("usuario", "Usuário não encontrado.");

        Endereco endereco = null;
        if (dto.idEnderecoEntrega() != null) {
            endereco = enderecoRepository.findById(dto.idEnderecoEntrega());
            if (endereco == null)
                throw ValidationException.of("endereco", "Endereço não encontrado.");
        }

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setEnderecoEntrega(endereco);
        pedido.setObservacao(dto.observacao());
        pedido.setStatus("PAGAMENTO_PENDENTE");

        pedidoRepository.persist(pedido);

        for (ItemPedidoDTO itemDTO : dto.itens()) {

            Produto produto = produtoRepository.findById(itemDTO.idProduto());
            if (produto == null)
                throw ValidationException.of("produto", "Produto não encontrado.");

            estoqueService.verificarDisponibilidade(
                produto.getId(),
                itemDTO.quantidade()
            );

            ItemPedido item = new ItemPedido();
            item.setPedido(pedido);
            item.setProduto(produto);
            item.setQuantidade(itemDTO.quantidade());
            item.setPrecoUnitario(produto.getPreco());

            itemPedidoService.salvar(item);
        }

        pedido.recalcTotal();

        for (ItemPedido item : pedido.getItens()) {
            estoqueService.baixarEstoque(
                item.getProduto().getId(),
                item.getQuantidade()
            );
        }

        Pagamento pagamento = new Pagamento();
        pagamento.setPedido(pedido);
        pagamento.setMetodoPagamento(dto.pagamento().metodoPagamento());
        pagamento.setStatus("PENDENTE");
        pagamento.setValor(pedido.getTotal());

        if ("PIX".equalsIgnoreCase(dto.pagamento().metodoPagamento())) {
            pagamento.setCodigoPagamento(
                "PIX-" + pedido.getId() + "-" + UUID.randomUUID()
            );
        }

        pagamentoRepository.persist(pagamento);
        pedido.setPagamento(pagamento);

        return toResponseDTO(pedido);
    }

    // -------- BUSCAR POR ID --------

    @Override
    public PedidoResponseDTO findById(Long idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido);

        if (pedido == null)
            throw ValidationException.of("id", "Pedido não encontrado.");

        return toResponseDTO(pedido);
    }

    // -------- HISTÓRICO DO USUÁRIO --------

    @Override
    public List<PedidoResponseDTO> findByUsuario(Long idUsuario) {
        return pedidoRepository.findByUsuario(idUsuario)
            .stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }

    // -------- CANCELAR PEDIDO --------

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
            estoqueService.reporEstoque(
                item.getProduto().getId(),
                item.getQuantidade()
            );
        }
    }

    // -------- MAPEAMENTO --------

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

        Pagamento pagamento = pedido.getPagamento();

        PagamentoResponseDTO pagamentoDTO =
            new PagamentoResponseDTO(
                pagamento.getId(),
                pagamento.getMetodoPagamento(),
                pagamento.getStatus(),
                pagamento.getValor(),
                pagamento.getCodigoPagamento(),
                pagamento.getDataCriacao()
            );

        return new PedidoResponseDTO(
            pedido.getId(),
            pedido.getDataCriacao(),
            pedido.getTotal(),
            pedido.getStatus(),
            pedido.getObservacao(),
            pedido.getUsuario().getId(),
            pedido.getEnderecoEntrega() != null
                ? pedido.getEnderecoEntrega().getId()
                : null,
            itensDTO,
            pagamentoDTO
        );
    }
}
