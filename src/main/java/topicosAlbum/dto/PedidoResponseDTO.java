package topicosAlbum.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponseDTO(

    Long id,
    LocalDateTime dataCriacao,
    BigDecimal total,
    String status,
    String observacao,

    Long idUsuario,

    Long idEnderecoEntrega,

    List<ItemPedidoResponseDTO> itens,

    PagamentoResponseDTO pagamento
) {}
