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

    public boolean existsByNome(String nomeGenero) {
        return find("LOWER(nomeGenero) = ?1", nomeGenero.toLowerCase()).firstResultOptional().isPresent();
    }

    // busca gênero por álbum via Faixa 
    public List<Genero> findByAlbumId(Long idAlbum) {
        return getEntityManager()
            .createQuery("""
                SELECT DISTINCT g FROM Faixa f
                JOIN f.genero g
                WHERE f.album.id = :idAlbum
            """, Genero.class)
            .setParameter("idAlbum", idAlbum)
            .getResultList();
    }
}
