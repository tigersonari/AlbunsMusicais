package topicosAlbum.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import topicosAlbum.model.Estoque;

@ApplicationScoped
public class EstoqueRepository implements PanacheRepository<Estoque> {

    public Estoque findByProduto(Long idProduto) {
        return find("produtoId", idProduto).firstResult();
    }
}
