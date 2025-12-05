package topicosAlbum.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PedidoDTO(

    @NotNull
    Long idUsuario,

    Long idEnderecoEntrega,

    String observacao,

    @NotEmpty
    List<ItemPedidoDTO> itens,

    @NotNull
    PagamentoDTO pagamento
) {}
