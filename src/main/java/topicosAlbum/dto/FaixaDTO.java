package topicosAlbum.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FaixaDTO(

    @NotBlank(message = "O título da faixa é obrigatório.")
    String titulo,

    @NotNull(message = "O número da faixa é obrigatório.")
    Integer numeroFaixa,

    @NotNull(message = "A duração é obrigatória.")
    Double duracao,

    @NotBlank(message = "O idioma é obrigatório.")
    String idioma,

    @NotNull(message = "O tipo da versão é obrigatório.")
    Long idTipoVersao, // nome do enum

    @NotNull(message = "O gênero é obrigatório.")
    Long idGenero,

    @NotNull(message = "O álbum é obrigatório.")
    Long idAlbum,

    @NotNull(message = "A composição é obrigatória.")
    Long idComposicao

) {}
