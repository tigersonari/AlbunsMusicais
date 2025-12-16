package topicosAlbum.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PagamentoResponseDTO(

    Long id,
    String metodoPagamento,
    String status,
    BigDecimal valor,
    String codigoPagamento,
    LocalDateTime dataCriacao,
    String ultimos4,
    String bandeira


    /*Long id,
    String metodoPagamento,   // PIX, BOLETO, CARTAO
    String status,            // PENDENTE, PROCESSANDO, APROVADO, REJEITADO
    BigDecimal valor,

    // INFORMAÇÕES EXCLUSIVAS DEPENDENDO DO MÉTODO

    // --- CARTÃO ---
    String ultimos4,
    String bandeira,

    // --- PIX ---
    String codigoPix,      // copia e cola
    String qrCodeBase64,   // QR simulado

    // --- BOLETO ---
    String linhaDigitavel,
    String pdfBase64,  // ou uma URL local para download

    LocalDateTime dataCriacao*/
    

) {}
