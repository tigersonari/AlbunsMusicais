package topicosAlbum.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record EnderecoDTO(

    @NotBlank String rua,
    @NotBlank String numero,
    String complemento,
    @NotBlank String bairro,
    @NotBlank String cidade,

    @NotBlank
    @Pattern(regexp = "^[A-Z]{2}$")
    String uf,

    @NotBlank
    @Pattern(regexp = "^\\d{5}-?\\d{3}$")
    String cep

    /*@NotNull
    Long idUsuario*/
) {}
