package topicosAlbum.dto;

import jakarta.validation.constraints.NotBlank;

public record RedefinirSenhaDTO(
    @NotBlank(message = "Token é obrigatório.")
    String token,

    @NotBlank(message = "Nova senha é obrigatória.")
    String novaSenha
) {}