package topicosAlbum.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AtualizarPerfilDTO(
    @NotBlank(message = "Nome é obrigatório.")
    String nome,

    @NotBlank(message = "Login é obrigatório.")
    String login,

    @Email(message = "E-mail inválido.")
    String email,

    String telefone,

    @NotBlank(message = "Confirme sua senha para alterar o perfil.")
    String senhaConfirmacao
) {}