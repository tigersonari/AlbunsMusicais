package topicosAlbum.repository;

import java.util.List;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import topicosAlbum.model.Faixa;
import topicosAlbum.model.ProjetoMusical;
import topicosAlbum.model.TipoVersao;

@ApplicationScoped
public class FaixaRepository implements PanacheRepository<Faixa> {

    public List<Faixa> findByTitulo(String titulo) {
        return find("LOWER(titulo) LIKE ?1", "%" + titulo.toLowerCase() + "%").list();
    }

    public List<Faixa> findByAlbumId(Long idAlbum) {
        return find("album.id = ?1", idAlbum).list();
    }

    // Participação — artista que participou da faixa
    public List<Faixa> findByParticipacaoArtistaId(Long idProjetoMusical) {
        return getEntityManager().createQuery("""
            SELECT DISTINCT f FROM Faixa f
            JOIN f.participacoes p
            JOIN p.projetoMusical proj
            WHERE proj.id = :id
        """, Faixa.class)
        .setParameter("id", idProjetoMusical)
        .getResultList();
    }

    // Artistas principais da faixa via Album
    public List<ProjetoMusical> findArtistasPrincipaisByFaixaId(Long idFaixa) {
    return getEntityManager().createQuery("""
        SELECT DISTINCT pm FROM Album a
        JOIN a.projetoMusical pm
        JOIN Faixa f ON f.album = a
        WHERE f.id = :idFaixa
    """, ProjetoMusical.class)
    .setParameter("idFaixa", idFaixa)
    .getResultList();
}


    // Album da faixa
    public Object findAlbumByFaixaId(Long idFaixa) {
        return find("id = ?1", idFaixa).firstResult().getAlbum();
    }

    // Faixas compostas por determinado projeto musical (compositor)
    public List<Faixa> findByArtistaCompositorId(Long idProjetoMusical) {
        return getEntityManager().createQuery("""
            SELECT DISTINCT f FROM Faixa f
            JOIN f.composicao c
            JOIN c.projetoMusical cp
            WHERE cp.id = :idProjeto
        """, Faixa.class)
        .setParameter("idProjeto", idProjetoMusical)
        .getResultList();
    }

    // Tipo de versão (enum)
    public List<Faixa> findByTipoVersao(TipoVersao tipoVersao) {
        return find("tipoVersao = ?1", tipoVersao).list();
    }

    // Composicao da faixa (unidirecional)
    public Object findComposicaoByFaixaId(Long idFaixa) {
        return getEntityManager().createQuery("""
            SELECT c FROM Faixa f
            JOIN f.composicao c
            WHERE f.id = :idFaixa
        """)
        .setParameter("idFaixa", idFaixa)
        .getSingleResult();
    }

    public List<Faixa> findByGeneroId(Long idGenero) {
        return find("genero.id = ?1", idGenero).list();
    }

    public List<Faixa> findByIdioma(String idioma) {
        return find("LOWER(idioma) LIKE ?1", "%" + idioma.toLowerCase() + "%").list();
    }
}
