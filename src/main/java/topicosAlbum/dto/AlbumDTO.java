package topicosAlbum.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AlbumDTO(
    @NotBlank String titulo,
    @NotNull LocalDate lancamento,
    @NotBlank String descricao,
    @NotNull Long idFormato,
    @NotNull Long idProducao,
    @NotNull List<Long> idProjetoMusical,
    @NotNull List<Long> idGenero
) {}
