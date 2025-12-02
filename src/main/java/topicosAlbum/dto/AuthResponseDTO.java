package topicosAlbum.dto;

import topicosAlbum.model.Usuario;

public record AuthResponseDTO(
    Long id,
    String nome,
    String login,
    String perfil,
    String token
) {

    public static AuthResponseDTO valueOf(Usuario usuario, String token) {
        return new AuthResponseDTO(
            usuario.getId(),
            usuario.getNome(),
            usuario.getLogin(),
            usuario.getPerfil() != null ? usuario.getPerfil().name() : null,
            token
        );
    }
}
