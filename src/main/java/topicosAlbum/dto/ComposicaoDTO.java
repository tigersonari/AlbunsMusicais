package topicosAlbum.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotNull;

public record ComposicaoDTO(

    @NotNull(message = "A data da composição é obrigatória.")
    LocalDate data,

    @NotNull(message = "Os compositores precisam ser informados.")
    List<Long> idsProjetoMusical

) {}
