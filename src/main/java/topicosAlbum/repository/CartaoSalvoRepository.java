package topicosAlbum.repository;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import topicosAlbum.model.CartaoSalvo;

@ApplicationScoped
public class CartaoSalvoRepository implements PanacheRepository<CartaoSalvo> {

    public List<CartaoSalvo> listarPorUsuario(Long idUsuario) {
        return list("usuario.id", idUsuario);
    }

    public CartaoSalvo buscarDoUsuario(Long idCartao, Long idUsuario) {
        return find("id = ?1 and usuario.id = ?2", idCartao, idUsuario).firstResult();
    }
}
