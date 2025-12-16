package topicosAlbum.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioDTO (
    @NotBlank String nome,
    @NotBlank String login,
    @NotBlank String senha,
    
    @Email
    @NotBlank
    String email,
    
    @NotBlank
    @Length(min= 11, max = 11)
    String telefone
) {

}