package topicosAlbum.service;


import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import topicosAlbum.dto.AtualizarPerfilDTO;
import topicosAlbum.dto.UsuarioDTO;
import topicosAlbum.dto.UsuarioResponseDTO;
import topicosAlbum.exception.ValidationException;
import topicosAlbum.model.Perfil;
import topicosAlbum.model.Usuario;
import topicosAlbum.repository.UsuarioRepository;
import topicosAlbum.security.PasswordUtils;



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
    u.setSenha(PasswordUtils.hash(dto.senha()));

    // Perfil padrão para cadastro público
    u.setPerfil(Perfil.USER);

    u.setEmail(dto.email());
    u.setTelefone(dto.telefone());
    u.setNome(dto.nome());

    repository.persist(u);

    return UsuarioResponseDTO.valueOf(u);
}

@Inject
HashService hashService;

@Override
@Transactional
public void alterarSenha(Long idUsuario, String senhaAtual, String novaSenha) {
    Usuario usuario = repository.findById(idUsuario);

    if (usuario == null) {
        throw ValidationException.of("usuario", "Usuário não encontrado.");
    }

    String hashSenhaAtual = hashService.getHashSenha(senhaAtual);

    if (!usuario.getSenha().equals(hashSenhaAtual)) {
        throw ValidationException.of("senhaAtual", "Senha atual incorreta.");
    }

    usuario.setSenha(hashService.getHashSenha(novaSenha));
}

@Override
@Transactional
public UsuarioResponseDTO atualizarPerfil(Long idUsuario, AtualizarPerfilDTO dto) {
    Usuario usuario = repository.findById(idUsuario);

    if (usuario == null) {
        throw ValidationException.of("usuario", "Usuário não encontrado.");
    }

    String hashConfirmacao = hashService.getHashSenha(dto.senhaConfirmacao());

    if (!usuario.getSenha().equals(hashConfirmacao)) {
        throw ValidationException.of("senhaConfirmacao", "Senha incorreta.");
    }

    usuario.setNome(dto.nome());
    usuario.setLogin(dto.login());
    usuario.setEmail(dto.email());
    usuario.setTelefone(dto.telefone());

    return UsuarioResponseDTO.valueOf(usuario);
}


    // ---------------- RECUPERAÇÃO DE SENHA ----------------
    // O usuário solicita a recuperação de senha, gerando um token e enviando por e-mail (simulado aqui)
    // Depois, o usuário usa esse token para redefinir a senha
    @Override
    @Transactional
    public String solicitarRecuperacaoSenha(String email) {
        Usuario usuario = repository.find("email", email).firstResult();

        if (usuario == null) {
            throw ValidationException.of("email", "E-mail não encontrado.");
        }

        String token = java.util.UUID.randomUUID().toString();

        usuario.setTokenRecuperacaoSenha(token);
        usuario.setTokenRecuperacaoExpiracao(java.time.LocalDateTime.now().plusMinutes(30));

        return token;
    }

    // Redefinir senha usando o token gerado na solicitação de recuperação
    @Override
    @Transactional
    public void redefinirSenha(String token, String novaSenha) {
        Usuario usuario = repository.find("tokenRecuperacaoSenha", token).firstResult();

        if (usuario == null) {
            throw ValidationException.of("token", "Token inválido.");
        }

        if (usuario.getTokenRecuperacaoExpiracao() == null ||
            usuario.getTokenRecuperacaoExpiracao().isBefore(java.time.LocalDateTime.now())) {
            throw ValidationException.of("token", "Token expirado.");
        }

        usuario.setSenha(hashService.getHashSenha(novaSenha));
        usuario.setTokenRecuperacaoSenha(null);
        usuario.setTokenRecuperacaoExpiracao(null);
    }

    @Override
public void promoverParaAdmin(Long idUsuario) {
    Usuario usuario = findById(idUsuario);

    if (usuario == null) {
        throw new RuntimeException("Usuário não encontrado.");
    }

    usuario.setPerfil(Perfil.ADM);
}

}