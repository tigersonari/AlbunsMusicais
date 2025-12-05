package topicosAlbum.repository;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import topicosAlbum.model.Pagamento;

@ApplicationScoped
public class PagamentoRepository implements PanacheRepository<Pagamento> {

    public List<Pagamento> findByStatus(String status) {
        return find("status", status).list();
    }

    public List<Pagamento> findByMetodo(String metodo) {
        return find("metodoPagamento", metodo).list();
    }
}
