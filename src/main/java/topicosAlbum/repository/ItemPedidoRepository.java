package topicosAlbum.repository;

import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import topicosAlbum.model.ItemPedido;

@ApplicationScoped
public class ItemPedidoRepository implements PanacheRepository<ItemPedido> {
}
