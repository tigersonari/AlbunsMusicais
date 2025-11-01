package topicosAlbum.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GrupoMusicalDTO(
    @NotBlank(message = "O nome do grupo é obrigatório.")
    String nomeGrupo,

    @NotNull(message = "A data de início é obrigatória.")
    LocalDate dataInicio,

    LocalDate dataTermino, // pode ser null (grupo ativo)

    @NotNull(message = "O ID da empresa é obrigatório.")
    Long idEmpresa, 

    @NotNull(message = "Os membros do grupo são obrigatórios.")
    List<Long> idsArtistas
) {}
