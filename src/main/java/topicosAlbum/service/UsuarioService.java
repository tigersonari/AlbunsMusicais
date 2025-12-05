package topicosAlbum.service;


import java.util.List;

import topicosAlbum.dto.UsuarioDTO;
import topicosAlbum.dto.UsuarioResponseDTO;
import topicosAlbum.model.Usuario;

public interface UsuarioService {
    List<Usuario> findAll();
    Usuario findByLogin(String login);
    Usuario findByLoginAndSenha(String login, String senha);
    Usuario findById(Long id);
    UsuarioResponseDTO create(UsuarioDTO dto); // criar usuario cliente

}