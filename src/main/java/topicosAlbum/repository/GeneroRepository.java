package topicosAlbum.repository;


import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import topicosAlbum.model.Genero;

@ApplicationScoped
public class GeneroRepository implements PanacheRepository<Genero> {

    public List<Genero> findByNome(String nome) {
        return find("LOWER(nomeGenero) LIKE ?1", "%" + nome.toLowerCase() + "%").list();
    }

    // buscar gêneros usados em faixas de um álbum
    public List<?> findByAlbumId(Long idAlbum) {
        return getEntityManager()
            .createQuery("""
                SELECT DISTINCT g FROM Genero g
                JOIN g.faixas f
                JOIN f.album a
                WHERE a.id = :idAlbum
            """)
            .setParameter("idAlbum", idAlbum)
            .getResultList();
    }
}
