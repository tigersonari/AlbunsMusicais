package topicosAlbum.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EnderecoDTO(

    @NotBlank String rua,
    @NotBlank String numero,
    String complemento,
    @NotBlank String bairro,
    @NotBlank String cidade,

    @NotBlank
    String uf,

    @NotBlank
    String cep,

    @NotNull
    Long idUsuario
) {}
