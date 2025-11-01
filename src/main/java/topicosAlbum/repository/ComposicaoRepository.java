package topicosAlbum.repository;

import java.time.LocalDate;
import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import topicosAlbum.model.Composicao;

@ApplicationScoped
public class ComposicaoRepository implements PanacheRepository<Composicao> {

    public List<Composicao> findByArtistaOuGrupo(Long idProjetoMusical) {
        return getEntityManager()
            .createQuery("""
                SELECT DISTINCT c FROM Composicao c
                JOIN c.projetoMusical p
                WHERE p.id = :idProjeto
            """, Composicao.class)
            .setParameter("idProjeto", idProjetoMusical)
            .getResultList();
    }

    public List<Composicao> findByData(LocalDate data) {
        return find("data = ?1", data).list();
    }

    // buscar composição pela faixa
    public Composicao findByFaixaId(Long idFaixa) {
        return getEntityManager()
            .createQuery("""
                SELECT c FROM Faixa f
                JOIN f.composicao c
                WHERE f.id = :idFaixa
            """, Composicao.class)
            .setParameter("idFaixa", idFaixa)
            .getSingleResult();
    }
}
