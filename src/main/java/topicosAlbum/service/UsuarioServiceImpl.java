package topicosAlbum.service;


import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import topicosAlbum.dto.UsuarioDTO;
import topicosAlbum.dto.UsuarioResponseDTO;
import topicosAlbum.exception.ValidationException;
import topicosAlbum.model.Perfil;
import topicosAlbum.model.Usuario;
import topicosAlbum.repository.UsuarioRepository;



@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    UsuarioRepository repository;

    @Override
    public List<Usuario> findAll() {
        return repository
                    .listAll();
                    
    }

    @Override
    public Usuario findByLogin(String login) {
        return repository
                    .findByLogin(login);
    }

    @Override
    public Usuario findByLoginAndSenha(String login, String senha) {
        return repository
                    .findByLoginSenha(login, senha);
    }

    @Override
    public Usuario findById(Long id) {
        Usuario usuario = repository.findById(id);
        
        if (usuario == null)
            return null;

        return usuario;
    }

    @Override
@Transactional
public UsuarioResponseDTO create(UsuarioDTO dto) {

    // valida login único
    Usuario existente = repository.findByLogin(dto.login());
    if (existente != null)
        throw ValidationException.of("login", "Login já cadastrado.");

    Usuario u = new Usuario();
    u.setLogin(dto.login());
    u.setSenha(dto.senha());

    // Perfil padrão para cadastro público
    u.setPerfil(Perfil.USER);

    repository.persist(u);

    return UsuarioResponseDTO.valueOf(u);
}


}