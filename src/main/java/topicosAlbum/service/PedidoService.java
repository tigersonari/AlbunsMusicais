package topicosAlbum.service;

import java.util.List;

import topicosAlbum.dto.PedidoDTO;
import topicosAlbum.dto.PedidoResponseDTO;

public interface PedidoService {

    PedidoResponseDTO create(PedidoDTO dto);

    PedidoResponseDTO findById(Long idPedido);

    List<PedidoResponseDTO> findByUsuario(Long idUsuario);

    void cancelar(Long idPedido);
}
