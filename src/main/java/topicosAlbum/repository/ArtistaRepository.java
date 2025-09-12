package topicosAlbum.repository;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import topicosAlbum.model.Artista;

@ApplicationScoped
public class ArtistaRepository implements PanacheRepository<Artista> {

    public List<Artista> findByNome(String nome) {
        return find("SELECT a FROM Artista a WHERE a.nome LIKE ?1", "%" + nome + "%").list();
    }
}
