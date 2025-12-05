package topicosAlbum.service;

import topicosAlbum.dto.PagamentoResponseDTO;

public interface PagamentoService {

    PagamentoResponseDTO findById(Long idPagamento);

    PagamentoResponseDTO gerarPixParaPedido(Long idPedido);

    void confirmarPagamento(Long idPagamento);
}
