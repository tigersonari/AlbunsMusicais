package topicosAlbum.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "pagamento")
public class Pagamento extends DefaultEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    /**
     * Ex: PIX, CARTAO, BOLETO
     */
    @Column(name = "metodo_pagamento", length = 30, nullable = false)
    private String metodoPagamento;

    /**
     * Ex: PENDENTE, APROVADO, REJEITADO
     */
    @Column(name = "status", length = 30)
    private String status;

    /**
     * Valor pago (normalmente igual ao total do pedido)
     */
    @Column(name = "valor", precision = 19, scale = 4, nullable = false)
    private BigDecimal valor;

    /**
     * Código do pagamento:
     * - PIX: código copia e cola
     * - Cartão: id da transação
     * - Boleto: linha digitável
     * Gerado SOMENTE no service
     */
    @Column(name = "codigo_pagamento", columnDefinition = "text")
    private String codigoPagamento;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @PrePersist
    public void prePersist() {
        if (dataCriacao == null)
            dataCriacao = LocalDateTime.now();
    }

    // GETTERS / SETTERS

    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }

    public String getMetodoPagamento() { return metodoPagamento; }
    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public String getCodigoPagamento() { return codigoPagamento; }
    public void setCodigoPagamento(String codigoPagamento) {
        this.codigoPagamento = codigoPagamento;
    }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
