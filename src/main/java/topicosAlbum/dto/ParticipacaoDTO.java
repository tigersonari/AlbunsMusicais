package topicosAlbum.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ParticipacaoDTO(

    @NotBlank(message = "O papel da participação é obrigatório.")
    String papel,

    @NotNull(message = "Informe se o participante é destaque.")
    Boolean destaque,

    @NotNull(message = "Os participantes devem ser informados.")
    List<Long> idsProjetoMusical
) {}
