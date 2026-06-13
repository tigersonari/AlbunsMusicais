package topicosAlbum.dto;

import java.math.BigDecimal;

import topicosAlbum.model.Produto;

public record ProdutoResponseDTO(
    Long id,
    BigDecimal preco,
    AlbumResponseDTO album,
    Integer quantidadeEstoque
) {
    public static ProdutoResponseDTO valueOf(Produto produto, Integer quantidadeEstoque) {
        return new ProdutoResponseDTO(
            produto.getId(),
            produto.getPreco(),
            produto.getAlbum() != null ? AlbumResponseDTO.valueOf(produto.getAlbum()) : null,
            quantidadeEstoque
        );
    }
}