package topicosAlbum.service;

import java.util.List;

import topicosAlbum.dto.PedidoDTO;
import topicosAlbum.dto.PedidoResponseDTO;

public interface PedidoService {

    //PedidoResponseDTO create(PedidoDTO dto);

    PedidoResponseDTO findById(Long idPedido);

    List<PedidoResponseDTO> findByUsuario(Long idUsuario);

    void cancelar(Long idPedido);

    PedidoResponseDTO createParaUsuario(PedidoDTO dto, Long idUsuarioToken);

PedidoResponseDTO findByIdSeguro(Long idPedido, Long idUsuarioToken, boolean isAdmin);

void cancelarSeguro(Long idPedido, Long idUsuarioToken);

long count();

long countByStatus(String status);

java.math.BigDecimal faturamentoTotal();

List<PedidoResponseDTO> findAll();

}
