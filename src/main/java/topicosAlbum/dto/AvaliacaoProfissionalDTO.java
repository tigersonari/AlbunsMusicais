package topicosAlbum.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AvaliacaoProfissionalDTO(

    @NotBlank(message = "O nome do avaliador é obrigatório.")
    String avaliador,

    @NotBlank(message = "O comentário é obrigatório.")
    String comentario,

    @Min(value = 0, message = "A nota mínima é 0.")
    @Max(value = 10, message = "A nota máxima é 10.")
    double nota,

    @NotNull(message = "O id do álbum deve ser informado.")
    Long idAlbum
) {}
