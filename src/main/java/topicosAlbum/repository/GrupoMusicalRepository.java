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
public class GrupoMusicalRepository implements PanacheRepository<GrupoMusical> {

    public List<GrupoMusical> findByNomeGrupo(String nome) {
        return find("LOWER(nomeGrupo) LIKE ?1", "%" + nome.toLowerCase() + "%").list();
    }

    public List<GrupoMusical> findByEmpresaId(Long idEmpresa) {
        // Panache aceita "propriedade" = ?1 implicitamente
        return find("empresa.id", idEmpresa).list();
    }

    public List<GrupoMusical> findGruposAtivos() {
        return find("dataTermino IS NULL").list();
    }

    public List<GrupoMusical> findGruposInativos() {
        return find("dataTermino IS NOT NULL").list();
    }

    public List<GrupoMusical> findByMembroId(Long idArtista) {
        return getEntityManager()
            .createQuery("""
                SELECT DISTINCT g FROM GrupoMusical g
                JOIN g.membros m
                WHERE m.id = :idArtista
            """, GrupoMusical.class)
            .setParameter("idArtista", idArtista)
            .getResultList();
    }

    // FAIXAS "do grupo" = faixas de álbuns cujo projetoMusical é o próprio grupo
    public List<Faixa> findFaixasByGrupoId(Long idGrupo) {
        return getEntityManager()
            .createQuery("""
                SELECT DISTINCT f FROM Album a
                JOIN a.faixas f
                JOIN a.projetoMusical p
                WHERE p.id = :idGrupo
            """, Faixa.class)
            .setParameter("idGrupo", idGrupo)
            .getResultList();
    }

    public List<Album> findAlbunsByGrupoId(Long idGrupo) {
        return getEntityManager()
            .createQuery("""
                SELECT DISTINCT a FROM Album a
                JOIN a.projetoMusical p
                WHERE p.id = :idGrupo
            """, Album.class)
            .setParameter("idGrupo", idGrupo)
            .getResultList();
    }

    // álbuns onde o grupo participou (feat/apoio) via participações nas faixas
    public List<Album> findAlbunsComParticipacaoByGrupoId(Long idGrupo) {
        return getEntityManager()
            .createQuery("""
                SELECT DISTINCT a FROM Album a
                JOIN a.faixas f
                JOIN f.participacoes part
                JOIN part.projetoMusical pm
                WHERE pm.id = :idGrupo
            """, Album.class)
            .setParameter("idGrupo", idGrupo)
            .getResultList();
    }

    public List<Artista> findMembrosByGrupoId(Long idGrupo) {
        return getEntityManager()
            .createQuery("""
                SELECT m FROM GrupoMusical g
                JOIN g.membros m
                WHERE g.id = :idGrupo
            """, Artista.class)
            .setParameter("idGrupo", idGrupo)
            .getResultList();
    }

    // composições onde QUALQUER compositor é membro do grupo
    public List<Composicao> findComposicoesDoGrupoByGrupoId(Long idGrupo) {
        return getEntityManager()
            .createQuery("""
                SELECT DISTINCT c FROM Composicao c
                JOIN c.projetoMusical pm
                WHERE pm IN (
                    SELECT m FROM GrupoMusical g
                    JOIN g.membros m
                    WHERE g.id = :idGrupo
                )
            """, Composicao.class)
            .setParameter("idGrupo", idGrupo)
            .getResultList();
    }
}
