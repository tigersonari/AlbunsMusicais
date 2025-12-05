package topicosAlbum.dto;

import java.math.BigDecimal;

public record ItemPedidoResponseDTO(
    Long idProduto,
    String nomeProduto,
    Integer quantidade,
    BigDecimal precoUnitario,
    BigDecimal subtotal
) {}
