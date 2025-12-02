package topicosAlbum.repository;


import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import topicosAlbum.model.Usuario;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {

    public Usuario findByLoginSenha(String login, String senha) {
        return find("SELECT u FROM Usuario u WHERE u.login = ?1 AND u.senha = ?2 ", login, senha).firstResult();
    }

    public Usuario findByLogin(String login) {
       return find("SELECT u FROM Usuario u WHERE u.login = ?1 ", login).firstResult();
    }

}