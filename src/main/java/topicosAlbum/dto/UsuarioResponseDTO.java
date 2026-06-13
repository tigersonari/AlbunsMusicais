package topicosAlbum.dto;

import topicosAlbum.model.Usuario;

public record UsuarioResponseDTO(
    Long id,
    String login,
    String senha,
    String nome,
    String email,
    String telefone,
    String perfil
) {

    public static UsuarioResponseDTO valueOf(Usuario usuario) {
        if (usuario == null)
            return null;

        return new UsuarioResponseDTO(
            usuario.getId(),
            usuario.getLogin(),
            usuario.getSenha(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getTelefone(),
            usuario.getPerfil() != null ? usuario.getPerfil().name() : null
        );
    }
}