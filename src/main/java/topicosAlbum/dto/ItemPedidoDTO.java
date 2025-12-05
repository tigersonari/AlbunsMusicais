package topicosAlbum.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemPedidoDTO(

    @NotNull
    Long idProduto,

    @NotNull
    @Min(1)
    Integer quantidade
) {
}