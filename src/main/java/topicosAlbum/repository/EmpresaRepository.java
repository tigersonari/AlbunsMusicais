package topicosAlbum.repository;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import topicosAlbum.model.Album;
import topicosAlbum.model.Artista;
import topicosAlbum.model.Empresa;
import topicosAlbum.model.GrupoMusical;

@ApplicationScoped
public class EmpresaRepository implements PanacheRepository<Empresa> {

    public List<Empresa> findByNome(String nome) {
        return find("LOWER(nomeEmpresa) LIKE ?1", "%" + nome.toLowerCase() + "%").list();
    }

    public Empresa findByCnpj(String cnpj) {
        return find("cnpj = ?1", cnpj).firstResult();
    }

    public List<Empresa> findByLocalizacao(String localizacao) {
        return find("LOWER(localizacao) LIKE ?1", "%" + localizacao.toLowerCase() + "%").list();
    }

    public List<Empresa> findByContato(String contato) {
        return find("LOWER(contato) LIKE ?1", "%" + contato.toLowerCase() + "%").list();
    }

    public List<Artista> findArtistasByEmpresaId(Long idEmpresa) {
        return getEntityManager()
            .createQuery("""
                SELECT a FROM Artista a
                JOIN a.empresa e
                WHERE e.id = :idEmpresa
            """, Artista.class)
            .setParameter("idEmpresa", idEmpresa)
            .getResultList();
    }

    public List<GrupoMusical> findGruposByEmpresaId(Long idEmpresa) {
        return getEntityManager()
            .createQuery("""
                SELECT g FROM GrupoMusical g
                JOIN g.empresa e
                WHERE e.id = :idEmpresa
            """, GrupoMusical.class)
            .setParameter("idEmpresa", idEmpresa)
            .getResultList();
    }

    public List<Album> findAlbunsProduzidosByEmpresaId(Long idEmpresa) {
        return getEntityManager()
            .createQuery("""
                SELECT DISTINCT a FROM Album a
                JOIN a.producao p
                JOIN p.empresa e
                WHERE e.id = :idEmpresa
            """, Album.class)
            .setParameter("idEmpresa", idEmpresa)
            .getResultList();
    }

    public List<Album> findAlbunsLancadosByEmpresaId(Long idEmpresa) {
        return getEntityManager()
            .createQuery("""
                SELECT DISTINCT a FROM Album a
                JOIN a.projetoMusical p
                JOIN p.empresa e
                WHERE e.id = :idEmpresa
            """, Album.class)
            .setParameter("idEmpresa", idEmpresa)
            .getResultList();
    }
}
