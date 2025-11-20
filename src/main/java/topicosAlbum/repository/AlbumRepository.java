package topicosAlbum.repository;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import topicosAlbum.model.Album;
import topicosAlbum.model.Formato;

@ApplicationScoped
public class AlbumRepository implements PanacheRepository<Album> {

    public List<Album> findByTitulo(String titulo) {
        return find("LOWER(titulo) LIKE ?1", "%" + titulo.toLowerCase() + "%").list();
    }

    public List<Album> findByFormato(Formato formato) {
        return find("formato = ?1", formato).list();
    }

    public List<Album> findByAnoLancamento(int ano) {
       
        return find("EXTRACT(YEAR FROM lancamento) = ?1", ano).list();
    }

    public List<Album> findByProjetoMusicalId(Long idProjetoMusical) {
        return find("""
            SELECT DISTINCT a FROM Album a
            JOIN a.projetoMusical p
            WHERE p.id = ?1
        """, idProjetoMusical).list();
    }

    public List<Album> findColaboracoesEntre(Long idProjetoMusical1, Long idProjetoMusical2) {
        return find("""
            SELECT DISTINCT a FROM Album a
            JOIN a.projetoMusical p1
            JOIN a.projetoMusical p2
            WHERE p1.id = ?1 AND p2.id = ?2
        """, idProjetoMusical1, idProjetoMusical2).list();
    }

    public List<Album> findByEmpresaProducao(Long idEmpresa) {
        return find("""
            SELECT DISTINCT a FROM Album a
            JOIN a.producao pr
            WHERE pr.empresa.id = ?1
        """, idEmpresa).list();
    }

    public List<Album> findByProdutor(String produtor) {
        return find("""
            SELECT DISTINCT a FROM Album a
            JOIN a.producao p
            WHERE LOWER(p.produtor) LIKE ?1
        """, "%" + produtor.toLowerCase() + "%").list();
    }

    public List<Album> findByEngenheiroMixagem(String nome) {
        return find("""
            SELECT DISTINCT a FROM Album a
            JOIN a.producao p
            WHERE LOWER(p.engenheiroMixagem) LIKE ?1
        """, "%" + nome.toLowerCase() + "%").list();
    }

    public List<Album> findByEngenheiroMasterizacao(String nome) {
        return find("""
            SELECT DISTINCT a FROM Album a
            JOIN a.producao p
            WHERE LOWER(p.engenheiroMasterizacao) LIKE ?1
        """, "%" + nome.toLowerCase() + "%").list();
    }

    public List<Album> findByGeneroId(Long idGenero) {
        return find("""
            SELECT DISTINCT a FROM Album a
            JOIN a.generos g
            WHERE g.id = ?1
        """, idGenero).list();
    }

    // corrigido: não usa mais a.faixas (não existe)
    public List<Album> findByParticipacao(Long idProjetoMusical) {
        return getEntityManager()
            .createQuery("""
                SELECT DISTINCT f.album FROM Faixa f
                JOIN f.participacoes part
                JOIN part.projetoMusical pm
                WHERE pm.id = :idProjeto
            """, Album.class)
            .setParameter("idProjeto", idProjetoMusical)
            .getResultList();
    }

    // corrigido: busca pelos álbuns via faixa.album
    public List<Album> findByFaixaTitulo(String tituloFaixa) {
        return getEntityManager()
            .createQuery("""
                SELECT DISTINCT f.album FROM Faixa f
                WHERE LOWER(f.titulo) LIKE :titulo
            """, Album.class)
            .setParameter("titulo", "%" + tituloFaixa.toLowerCase() + "%")
            .getResultList();
    }
}
