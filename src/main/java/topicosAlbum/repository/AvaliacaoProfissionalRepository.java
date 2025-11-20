package topicosAlbum.repository;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import topicosAlbum.model.AvaliacaoProfissional;

@ApplicationScoped
public class AvaliacaoProfissionalRepository implements PanacheRepository<AvaliacaoProfissional> {

   
    public List<AvaliacaoProfissional> findByAlbumId(Long idAlbum) {
        return getEntityManager()
            .createQuery("""
                SELECT av FROM Album a
                JOIN a.avaliacaoProfissional av
                WHERE a.id = :idAlbum
            """, AvaliacaoProfissional.class)
            .setParameter("idAlbum", idAlbum)
            .getResultList();
    }

    public List<AvaliacaoProfissional> findByAvaliador(String avaliador) {
        return find("LOWER(avaliador) LIKE ?1", "%" + avaliador.toLowerCase() + "%").list();
    }

    // buscar avaliações de um álbum por nota exata
    public List<AvaliacaoProfissional> findByAlbumAndNota(Long idAlbum, double nota) {
        return getEntityManager()
            .createQuery("""
                SELECT av FROM Album a
                JOIN a.avaliacaoProfissional av
                WHERE a.id = :idAlbum AND av.nota = :nota
            """, AvaliacaoProfissional.class)
            .setParameter("idAlbum", idAlbum)
            .setParameter("nota", nota)
            .getResultList();
    }

    // buscar avaliações com nota mínima em um álbum
    public List<AvaliacaoProfissional> findByAlbumAndNotaMinima(Long idAlbum, double notaMinima) {
        return getEntityManager()
            .createQuery("""
                SELECT av FROM Album a
                JOIN a.avaliacaoProfissional av
                WHERE a.id = :idAlbum AND av.nota >= :nota
            """, AvaliacaoProfissional.class)
            .setParameter("idAlbum", idAlbum)
            .setParameter("nota", notaMinima)
            .getResultList();
    }
}
