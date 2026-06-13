package topicosAlbum.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CupomDTO(
    @NotBlank String codigo,
    @NotNull BigDecimal total
) {}