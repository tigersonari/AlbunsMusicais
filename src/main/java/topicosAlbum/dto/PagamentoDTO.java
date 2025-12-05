package topicosAlbum.dto;

import jakarta.validation.constraints.NotBlank;

public record PagamentoDTO(

    @NotBlank
    String metodoPagamento // PIX, CARTAO, BOLETO
) {}
