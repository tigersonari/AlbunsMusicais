package topicosAlbum.repository;


import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import topicosAlbum.model.Faixa;

@ApplicationScoped
public class FaixaRepository implements PanacheRepository<Faixa> {

    public List<Faixa> findByTitulo(String titulo) {
        return find("LOWER(titulo) LIKE ?1", "%" + titulo.toLowerCase() + "%").list();
    }

    // buscar faixas por álbum. ex:
    public List<Faixa> findByAlbumId(Long idAlbum) {
        return find("""
            SELECT f FROM Faixa f
            JOIN f.album a
            WHERE a.id = ?1
        """, idAlbum).list();
    }

    public List<Faixa> findByParticipacaoArtistaId(Long idArtista) {
        return find("""
            SELECT DISTINCT f FROM Faixa f
            JOIN f.participacoes p
            JOIN p.projetoMusical pm
            WHERE pm.id = ?1
        """, idArtista).list();
    }

    // dono (artistas principais via álbum)
    public List<?> findArtistasPrincipaisByFaixaId(Long idFaixa) {
        return getEntityManager()
            .createQuery("""
                SELECT DISTINCT p FROM Album a
                JOIN a.projetoMusical p
                JOIN a.faixas f
                WHERE f.id = :idFaixa
            """)
            .setParameter("idFaixa", idFaixa)
            .getResultList();
    }

    // album da faixa
    public Object findAlbumByFaixaId(Long idFaixa) {
        return getEntityManager()
            .createQuery("""
                SELECT a FROM Album a
                JOIN a.faixas f
                WHERE f.id = :idFaixa
            """)
            .setParameter("idFaixa", idFaixa)
            .getSingleResult();
    }

    // buscar faixas de um artista (como compositor)
    public List<Faixa> findByArtistaCompositorId(Long idArtista) {
        return find("""
            SELECT DISTINCT f FROM Faixa f
            JOIN f.composicao c
            JOIN c.artista a
            WHERE a.id = ?1
        """, idArtista).list();
    }

    // buscar faixas por tipo de versão (enum TipoVersao)
    public List<Faixa> findByTipoVersao(Enum<?> tipoVersao) {
        return find("tipoVersao = ?1", tipoVersao).list();
    }

    // buscar composição associada à faixa
    public Object findComposicaoByFaixaId(Long idFaixa) {
        return getEntityManager()
            .createQuery("""
                SELECT c FROM Composicao c
                JOIN c.faixa f
                WHERE f.id = :idFaixa
            """)
            .setParameter("idFaixa", idFaixa)
            .getSingleResult();
    }

    public List<Faixa> findByGeneroId(Long idGenero) {
        return find("""
            SELECT f FROM Faixa f
            JOIN f.genero g
            WHERE g.id = ?1
        """, idGenero).list();
    }

    public List<Faixa> findByIdioma(String idioma) {
        return find("LOWER(idioma) LIKE ?1", "%" + idioma.toLowerCase() + "%").list();
    }
}
