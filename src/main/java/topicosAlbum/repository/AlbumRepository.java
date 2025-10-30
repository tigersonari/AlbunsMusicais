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
        return find("YEAR(lancamento) = ?1", ano).list();
    }


    // buscar álbuns por artista principal (múltiplos artistas possíveis)
    public List<Album> findByProjetoMusicalId(Long idProjeto) {
        return find("""
            SELECT DISTINCT a FROM Album a
            JOIN a.projetoMusical p
            WHERE p.id = ?1
        """, idProjeto).list();
    }

    // buscar álbuns que são colaborações entre dois artistas específicos
    public List<Album> findColaboracoesEntre(Long idArtista1, Long idArtista2) {
        return find("""
            SELECT DISTINCT a FROM Album a
            JOIN a.projetoMusical p1
            JOIN a.projetoMusical p2
            WHERE p1.id = ?1 AND p2.id = ?2
        """, idArtista1, idArtista2).list();
    }

    // buscar álbuns produzidos pela empresa 
    public List<Album> findByEmpresaProducao(Long idEmpresa) {
        return find("""
            SELECT DISTINCT a FROM Album a
            JOIN a.producao pr
            JOIN pr.empresa e
            WHERE e.id = ?1
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

    // buscar álbuns que tenham faixas de um gênero específico
    public List<Album> findByGeneroId(Long idGenero) {
        return find("""
            SELECT DISTINCT a FROM Album a
            JOIN a.faixas f
            JOIN f.genero g
            WHERE g.id = ?1
        """, idGenero).list();
    }

    // buscar álbuns onde um artista participa (feat, banda de apoio etc.)
    public List<Album> findByParticipacaoArtistaId(Long idArtista) {
        return find("""
            SELECT DISTINCT a FROM Album a
            JOIN a.faixas f
            JOIN f.participacoes part
            JOIN part.projetoMusical pm
            WHERE pm.id = ?1
        """, idArtista).list();
    }

    // buscar todas as avaliações de um álbum
    public List<?> findAvaliacoesByAlbumId(Long idAlbum) {
        return getEntityManager()
            .createQuery("""
                SELECT av FROM AvaliacaoProfissional av
                JOIN av.album a
                WHERE a.id = :idAlbum
            """)
            .setParameter("idAlbum", idAlbum)
            .getResultList();
    }

    public List<?> findAvaliacoesByAvaliador(Long idAlbum, String avaliador) {
        return getEntityManager()
            .createQuery("""
                SELECT av FROM AvaliacaoProfissional av
                JOIN av.album a
                WHERE a.id = :idAlbum
                AND LOWER(av.avaliador) LIKE :avaliador
            """)
            .setParameter("idAlbum", idAlbum)
            .setParameter("avaliador", "%" + avaliador.toLowerCase() + "%")
            .getResultList();
    }

    // buscar avaliações de um álbum com nota mínima
    public List<?> findAvaliacoesByNotaMinima(Long idAlbum, double notaMinima) {
        return getEntityManager()
            .createQuery("""
                SELECT av FROM AvaliacaoProfissional av
                JOIN av.album a
                WHERE a.id = :idAlbum
                AND av.nota >= :nota
            """)
            .setParameter("idAlbum", idAlbum)
            .setParameter("nota", notaMinima)
            .getResultList();
    }

    // buscar a produção associada a um álbum
    public Object findProducaoByAlbumId(Long idAlbum) {
        return getEntityManager()
            .createQuery("""
                SELECT p FROM Producao p
                JOIN p.album a
                WHERE a.id = :idAlbum
            """)
            .setParameter("idAlbum", idAlbum)
            .getSingleResult();
    }

    // buscar álbuns por nome da faixa. ex: quero saber de qual album é a faixa "the feels"
    public List<Album> findByFaixaTitulo(String tituloFaixa) {
    return find("""
        SELECT DISTINCT a FROM Album a
        JOIN a.faixas f
        WHERE LOWER(f.titulo) LIKE ?1
    """, "%" + tituloFaixa.toLowerCase() + "%").list();
}

    // buscar todos gêneros de um álbum atraves das faixas, sem repetição
    public List<?> findGenerosDoAlbum(Long idAlbum) {
    return getEntityManager()
        .createQuery("""
            SELECT DISTINCT g FROM Genero g
            JOIN g.faixas f
            JOIN f.album a
            WHERE a.id = :idAlbum
        """)
        .setParameter("idAlbum", idAlbum)
        .getResultList();
}


}
