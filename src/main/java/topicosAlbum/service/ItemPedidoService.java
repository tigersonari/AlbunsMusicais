package topicosAlbum.service;

import java.util.List;

import topicosAlbum.model.ItemPedido;

public interface ItemPedidoService {

    void salvar(ItemPedido item);

    List<ItemPedido> listarPorPedido(Long idPedido);
}
