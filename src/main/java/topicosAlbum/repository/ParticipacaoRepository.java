package topicosAlbum.repository;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import topicosAlbum.model.Participacao;

@ApplicationScoped
public class ParticipacaoRepository implements PanacheRepository<Participacao> {

    public List<Participacao> findByFaixaId(Long idFaixa) {
        return find("faixa.id", idFaixa).list();
    }

    public List<Participacao> findByProjetoMusicalId(Long idProjeto) {
        return find("projetoMusical.id", idProjeto).list();
    }

    public List<Participacao> findByPapel(String papel) {
        return find("LOWER(papel) LIKE ?1", "%" + papel.toLowerCase() + "%").list();
    }

    public List<Participacao> findPrincipais() {
        return find("destaque = true").list();
    }

    // buscar participações de um álbum
    public List<?> findByAlbumId(Long idAlbum) {
        return getEntityManager()
            .createQuery("""
                SELECT DISTINCT p FROM Participacao p
                JOIN p.faixa f
                JOIN f.album a
                WHERE a.id = :idAlbum
            """)
            .setParameter("idAlbum", idAlbum)
            .getResultList();
    }
}
