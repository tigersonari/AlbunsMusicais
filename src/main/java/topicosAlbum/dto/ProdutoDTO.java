package topicosAlbum.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;

public record ProdutoDTO(

    @NotNull(message = "O álbum é obrigatório.")
    Long idAlbum,

    @NotNull(message = "O preço é obrigatório.")
    BigDecimal preco,

    @NotNull(message = "A quantidade em estoque é obrigatória.")
    Integer quantidadeEstoque

) {}