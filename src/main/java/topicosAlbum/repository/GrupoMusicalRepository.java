package topicosAlbum.repository;


import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import topicosAlbum.model.GrupoMusical;

@ApplicationScoped
public class GrupoMusicalRepository implements PanacheRepository<GrupoMusical> {

    public List<GrupoMusical> findByNome(String nome) {
        return find("SELECT g FROM GrupoMusical g WHERE g.nome LIKE ?1", "%" + nome + "%").list();
    }
}
