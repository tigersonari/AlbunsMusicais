package topicosAlbum.service;


import java.util.List;

import topicosAlbum.dto.AtualizarPerfilDTO;
import topicosAlbum.dto.UsuarioDTO;
import topicosAlbum.dto.UsuarioResponseDTO;
import topicosAlbum.model.Usuario;

public interface UsuarioService {
    List<Usuario> findAll();
    Usuario findByLogin(String login);
    Usuario findByLoginAndSenha(String login, String senha);
    Usuario findById(Long id);
    UsuarioResponseDTO create(UsuarioDTO dto); // criar usuario cliente
    void alterarSenha(Long idUsuario, String senhaAtual, String novaSenha);
    UsuarioResponseDTO atualizarPerfil(Long idUsuario, AtualizarPerfilDTO dto);

    // Recuperação de senha, onde o usuário solicita a recuperação (gerando um token) e depois redefine a senha usando esse token
    String solicitarRecuperacaoSenha(String email);

    void redefinirSenha(String token, String novaSenha);

    void promoverParaAdmin(Long idUsuario);

}