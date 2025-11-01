package topicosAlbum.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ArtistaDTO(

    @NotBlank(message = "O nome completo é obrigatório.")
    String nomeCompleto,

    @NotBlank(message = "O nome artístico é obrigatório.")
    String nomeArtistico,

    @NotNull(message = "A data de nascimento é obrigatória.")
    LocalDate dataNascimento,

    @NotBlank(message = "A nacionalidade é obrigatória.")
    String nacionalidade,

    @NotBlank(message = "A função principal é obrigatória.")
    String funcaoPrincipal, // exemplo: cantor, produtor

    @NotNull(message = "A empresa do artista é obrigatória.")
    Long idEmpresa
) {}
