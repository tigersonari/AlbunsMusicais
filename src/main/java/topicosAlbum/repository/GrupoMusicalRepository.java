package topicosAlbum.repository;


import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import topicosAlbum.model.GrupoMusical;

@ApplicationScoped
public class GrupoMusicalRepository implements PanacheRepository<GrupoMusical> {

    public List<GrupoMusical> findByNomeGrupo(String nome) {
        return find("LOWER(nomeGrupo) LIKE ?1", "%" + nome.toLowerCase() + "%").list();
    }

    public List<GrupoMusical> findByEmpresaId(Long idEmpresa) {
        return find("empresa.id", idEmpresa).list();
    }

    public List<GrupoMusical> findGruposAtivos() {
        return find("dataTermino IS NULL").list();
    }

    public List<GrupoMusical> findGruposInativos() {
        return find("dataTermino IS NOT NULL").list();
    }

    // buscar grupos que contenham um artista específico
    public List<GrupoMusical> findByMembroId(Long idArtista) {
        return find("""
            SELECT DISTINCT g FROM GrupoMusical g
            JOIN g.membros m
            WHERE m.id = ?1
        """, idArtista).list();
    }

    // faixas cantadas por um grupo
    public List<?> findFaixasByGrupoId(Long idGrupo) {
        return getEntityManager().createQuery("""
            SELECT DISTINCT f FROM Faixa f
            JOIN f.album a
            JOIN a.projetoMusical p
            WHERE p.id = :idGrupo
        """).setParameter("idGrupo", idGrupo)
        .getResultList();
    }

    // albuns do grupo
    public List<?> findAlbunsByGrupoId(Long idGrupo) {
        return getEntityManager().createQuery("""
            SELECT DISTINCT a FROM Album a
            JOIN a.projetoMusical p
            WHERE p.id = :idGrupo
        """).setParameter("idGrupo", idGrupo)
        .getResultList();
    }

    // albuns onde o grupo participou como artista convidado
    public List<?> findAlbunsComParticipacaoByGrupoId(Long idGrupo) {
        return getEntityManager().createQuery("""
            SELECT DISTINCT a FROM Album a
            JOIN a.faixas f
            JOIN f.participacoes part
            JOIN part.projetoMusical pm
            WHERE pm.id = :idGrupo
        """).setParameter("idGrupo", idGrupo)
        .getResultList();
    }

    // listar todos os membros do grupo
    public List<?> findMembrosByGrupoId(Long idGrupo) {
        return getEntityManager()
            .createQuery("""
                SELECT m FROM GrupoMusical g
                JOIN g.membros m
                WHERE g.id = :idGrupo
            """)
            .setParameter("idGrupo", idGrupo)
            .getResultList();
    }

    // composições feitas por membros do grupo
    public List<?> findComposicoesDoGrupoByGrupoId(Long idGrupo) {
        return getEntityManager().createQuery("""
            SELECT DISTINCT c FROM Composicao c
            JOIN c.artista a
            JOIN GrupoMusical g ON a MEMBER OF g.membros
            WHERE g.id = :idGrupo
        """).setParameter("idGrupo", idGrupo)
        .getResultList();
    }
}
