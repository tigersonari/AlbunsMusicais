package topicosAlbum.dto;

import jakarta.validation.constraints.NotNull;

public record EstoqueDTO(

    @NotNull
    Long idProduto,

    @NotNull
    Integer quantidadeDisponivel
) {}
