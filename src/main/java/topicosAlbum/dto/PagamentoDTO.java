package topicosAlbum.dto;

import jakarta.validation.constraints.NotBlank;

public record PagamentoDTO(

     @NotBlank
    String metodoPagamento, // PIX, CARTAO, BOLETO

    Long idCartaoSalvo,     // Se usuário escolher um cartão salvo

    // campos para novo cartão 
    String numeroCartao,
    String nomeImpresso,
    String validade
) {}
