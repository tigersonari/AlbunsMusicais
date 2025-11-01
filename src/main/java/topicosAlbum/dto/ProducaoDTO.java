package topicosAlbum.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProducaoDTO(

    @NotBlank(message = "O produtor é obrigatório.")
    String produtor,

    @NotBlank(message = "O engenheiro de gravação é obrigatório.")
    String engenheiroGravacao,

    @NotBlank(message = "O engenheiro de mixagem é obrigatório.")
    String engenheiroMixagem,

    @NotBlank(message = "O engenheiro de masterização é obrigatório.")
    String engenheiroMasterizacao,

    @NotNull(message = "A data de gravação é obrigatória.")
    LocalDate dataProducao,

    @NotNull(message = "A empresa responsável é obrigatória.")
    Long idEmpresa
) {}
