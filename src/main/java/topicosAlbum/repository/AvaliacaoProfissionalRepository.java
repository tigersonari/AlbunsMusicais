package topicosAlbum.repository;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import topicosAlbum.model.AvaliacaoProfissional;

@ApplicationScoped
public class AvaliacaoProfissionalRepository implements PanacheRepository<AvaliacaoProfissional> {

    public List<AvaliacaoProfissional> findByAlbumId(Long idAlbum) {
        return find("album.id", idAlbum).list();
    }

    public List<AvaliacaoProfissional> findByAvaliador(String avaliador) {
        return find("LOWER(avaliador) LIKE ?1", "%" + avaliador.toLowerCase() + "%").list();
    }

    /*public List<AvaliacaoProfissional> findByNota(double nota) {
    return find("nota = ?1", nota).list();
    }*/     /*qualquer nota igual à informada */

    // buscar avaliações de um álbum por nota exata
    public List<AvaliacaoProfissional> findByAlbumAndNota(Long idAlbum, double nota) {
        return find("""
            SELECT av FROM AvaliacaoProfissional av
            JOIN av.album a
            WHERE a.id = ?1 AND av.nota = ?2
        """, idAlbum, nota).list();
    }

    // buscar avaliações com nota mínima em um álbum
    public List<AvaliacaoProfissional> findByAlbumAndNotaMinima(Long idAlbum, double notaMinima) {
        return find("""
            SELECT av FROM AvaliacaoProfissional av
            JOIN av.album a
            WHERE a.id = ?1 AND av.nota >= ?2
        """, idAlbum, notaMinima).list();
    }

    /*public List<AvaliacaoProfissional> findByNotaMinima(double nota) {
        return find("nota >= ?1", nota).list();
    }**/  /*igual ou maior à nota informada */
}
