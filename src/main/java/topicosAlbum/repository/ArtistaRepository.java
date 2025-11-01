package topicosAlbum.repository;


import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import topicosAlbum.model.Album;
import topicosAlbum.model.Artista;
import topicosAlbum.model.Composicao;
import topicosAlbum.model.Faixa;
import topicosAlbum.model.GrupoMusical;

@ApplicationScoped
public class ArtistaRepository implements PanacheRepository<Artista> {

    public List<Artista> findByNomeArtistico(String nomeArtistico) {
        return find("LOWER(nomeArtistico) LIKE ?1", "%" + nomeArtistico.toLowerCase() + "%").list();
    }

    public List<Artista> findByNomeCompleto(String nomeCompleto) {
        return find("LOWER(nomeCompleto) LIKE ?1", "%" + nomeCompleto.toLowerCase() + "%").list();
    }

    public List<Artista> findByNacionalidade(String nacionalidade) {
        return find("LOWER(nacionalidade) LIKE ?1", "%" + nacionalidade.toLowerCase() + "%").list();
    }

    public List<Artista> findByFuncaoPrincipal(String funcaoPrincipal) {
        return find("LOWER(funcaoPrincipal) LIKE ?1", "%" + funcaoPrincipal.toLowerCase() + "%").list();
    }

    public List<Artista> findByEmpresaId(Long idEmpresa) {
        return find("empresa.id", idEmpresa).list();
    }

    // grupos musicais que o artista integra
   public List<GrupoMusical> findGruposByArtistaId(Long idArtista) {
    return getEntityManager()
        .createQuery("""
            SELECT DISTINCT g FROM GrupoMusical g
            JOIN g.membros m
            WHERE m.id = :idArtista
        """, GrupoMusical.class)
        .setParameter("idArtista", idArtista)
        .getResultList();
}


    // albuns do artista (como principal)
    public List<Album> findAlbunsPrincipaisByArtistaId(Long idArtista) {
        return getEntityManager()
            .createQuery("""
                SELECT DISTINCT a FROM Album a
                JOIN a.projetoMusical p
                WHERE p.id = :idArtista
            """)
            .setParameter("idArtista", idArtista)
            .getResultList();
    }

    // albuns em que o artista participa (feats, apoio, etc.)
    public List<Album> findAlbunsComParticipacaoByArtistaId(Long idArtista) {
        return getEntityManager()
            .createQuery("""
                SELECT DISTINCT a FROM Album a
                JOIN a.faixas f
                JOIN f.participacoes p
                JOIN p.projetoMusical pm
                WHERE pm.id = :idArtista
            """)
            .setParameter("idArtista", idArtista)
            .getResultList();
    }

    // faixas com participação do artista
    public List<Faixa> findFaixasParticipadasByArtistaId(Long idArtista) {
        return getEntityManager()
            .createQuery("""
                SELECT DISTINCT f FROM Faixa f
                JOIN f.participacoes p
                JOIN p.projetoMusical pm
                WHERE pm.id = :idArtista
            """)
            .setParameter("idArtista", idArtista)
            .getResultList();
    }

    // composições criadas pelo artista (como compositor/letrista)
    public List<Composicao> findComposicoesByArtistaId(Long idArtista) {
        return getEntityManager()
            .createQuery("""
                SELECT DISTINCT c FROM Composicao c
                JOIN c.artista a
                WHERE a.id = :idArtista
            """)
            .setParameter("idArtista", idArtista)
            .getResultList();
    }

    // todas as faixas relacionadas ao artista (como principal ou colaborador)
    public List<?> findTodasFaixasRelacionadas(Long idArtista) {
        return getEntityManager()
            .createQuery("""
                SELECT DISTINCT f FROM Faixa f
                WHERE f.album.id IN (
                    SELECT a.id FROM Album a
                    JOIN a.projetoMusical p
                    WHERE p.id = :idArtista
                )
                OR f.id IN (
                    SELECT f2.id FROM Faixa f2
                    JOIN f2.participacoes p2
                    JOIN p2.projetoMusical pm
                    WHERE pm.id = :idArtista
                )
            """)
            .setParameter("idArtista", idArtista)
            .getResultList();
    }
}
