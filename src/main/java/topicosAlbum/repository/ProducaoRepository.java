package topicosAlbum.repository;

import java.time.LocalDate;
import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import topicosAlbum.model.Producao;

@ApplicationScoped
public class ProducaoRepository implements PanacheRepository<Producao> {

    public List<Producao> findByProdutor(String produtor) {
        return find("LOWER(produtor) LIKE ?1", "%" + produtor.toLowerCase() + "%").list();
    }

    public List<Producao> findByEngenheiroGravacao(String nome) {
        return find("LOWER(engenheiroGravacao) LIKE ?1", "%" + nome.toLowerCase() + "%").list();
    }

    public List<Producao> findByEngenheiroMixagem(String nome) {
        return find("LOWER(engenheiroMixagem) LIKE ?1", "%" + nome.toLowerCase() + "%").list();
    }

    public List<Producao> findByEngenheiroMasterizacao(String nome) {
        return find("LOWER(engenheiroMasterizacao) LIKE ?1", "%" + nome.toLowerCase() + "%").list();
    }

    public List<Producao> findByDataGravacao(LocalDate data) {
        return find("dataGravacao", data).list();
    }

    public List<Producao> findByPeriodoGravacao(LocalDate inicio, LocalDate fim) {
        return find("dataGravacao BETWEEN ?1 AND ?2", inicio, fim).list();
    } /*busca produções em um intervalo de datas de gravação */

    public List<Producao> findByEmpresaId(Long idEmpresa) {
        return find("empresa.id", idEmpresa).list();
    }

    public Producao findByAlbumId(Long idAlbum) {
        return find("album.id", idAlbum).firstResult();
    }
}
