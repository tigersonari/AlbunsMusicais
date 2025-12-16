package topicosAlbum.service;

import topicosAlbum.dto.PagamentoResponseDTO;

public interface PagamentoService {

    PagamentoResponseDTO findByIdSeguro(Long idPagamento, Long idUsuarioToken, boolean isAdmin);

    PagamentoResponseDTO gerarPixParaPedidoSeguro(Long idPedido, Long idUsuarioToken, boolean isAdmin);

    void solicitarConfirmacao(Long idPagamento, Long idUsuarioToken);

}
