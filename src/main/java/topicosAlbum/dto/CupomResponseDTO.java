package topicosAlbum.dto;

import java.math.BigDecimal;

public record CupomResponseDTO(
    String codigo,
    BigDecimal percentualDesconto,
    BigDecimal valorDesconto,
    BigDecimal totalComDesconto,
    String mensagem
) {}