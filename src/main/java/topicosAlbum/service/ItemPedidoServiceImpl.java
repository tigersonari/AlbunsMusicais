package topicosAlbum.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import topicosAlbum.model.ItemPedido;
import topicosAlbum.repository.ItemPedidoRepository;

@ApplicationScoped
public class ItemPedidoServiceImpl implements ItemPedidoService {

    @Inject
    ItemPedidoRepository repository;

    @Override
    @Transactional
    public void salvar(ItemPedido item) {
        repository.persist(item);
    }

    @Override
    public List<ItemPedido> listarPorPedido(Long idPedido) {
        return repository.find("pedido.id", idPedido).list();
    }
}
