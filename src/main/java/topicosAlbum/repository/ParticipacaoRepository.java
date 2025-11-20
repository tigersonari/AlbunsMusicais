package topicosAlbum.repository;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import topicosAlbum.model.Participacao;

@ApplicationScoped
public class ParticipacaoRepository implements PanacheRepository<Participacao> {

    // buscar participações de uma faixa
    public List<Participacao> findByFaixaId(Long idFaixa) {
        return getEntityManager()
            .createQuery("""
                SELECT p FROM Faixa f
                JOIN f.participacoes p
                WHERE f.id = :idFaixa
            """, Participacao.class)
            .setParameter("idFaixa", idFaixa)
            .getResultList();
    }

    // buscar participações de um artista ou grupo
    public List<Participacao> findByProjetoMusicalId(Long idProjeto) {
        return getEntityManager()
            .createQuery("""
                SELECT DISTINCT p FROM Participacao p
                JOIN p.projetoMusical proj
                WHERE proj.id = :idProjeto
            """, Participacao.class)
            .setParameter("idProjeto", idProjeto)
            .getResultList();
    }

    public List<Participacao> findByPapel(String papel) {
        return find("LOWER(papel) LIKE ?1", "%" + papel.toLowerCase() + "%").list();
    }

    public List<Participacao> findPrincipais() {
        return find("destaque = true").list();
    }

    // buscar participações de um álbum via faixas
    // corrigido: não usa a.faixas
    public List<Participacao> findByAlbumId(Long idAlbum) {
        return getEntityManager()
            .createQuery("""
                SELECT DISTINCT p FROM Faixa f
                JOIN f.participacoes p
                WHERE f.album.id = :idAlbum
            """, Participacao.class)
            .setParameter("idAlbum", idAlbum)
            .getResultList();
    }
}
