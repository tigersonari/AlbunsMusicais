package topicosAlbum.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedido")
public class Pedido extends DefaultEntity {

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "total", precision = 19, scale = 4, nullable = false)
    private BigDecimal total = BigDecimal.ZERO;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // endereço de entrega (pode ser null se for retirada)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_entrega_id")
    private Endereco enderecoEntrega;

    // composição bidirecional com ItemPedido
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens = new ArrayList<>();

    // link para o pagamento (1:1)
    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Pagamento pagamento;

    // logs/observações opcionais
    @Column(name = "observacao", length = 1000)
    private String observacao;

    /**
     * Status do pedido.
     * Sugestão de valores:
     * - "CRIADO"
     * - "PAGAMENTO_PENDENTE"
     * - "PAGO"
     * - "CANCELADO"
     */
    @Column(name = "status", length = 30, nullable = false)
    private String status = "CRIADO";

    // ---------- CALLBACKS JPA ----------

    @PrePersist
    public void prePersist() {
        if (dataCriacao == null) {
            dataCriacao = LocalDateTime.now();
        }
        if (total == null) {
            total = BigDecimal.ZERO;
        }
        if (status == null || status.isBlank()) {
            status = "CRIADO";
        }
    }

    // ---------- GETTERS / SETTERS ----------

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public BigDecimal getTotal() {
        return total;
    }
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Endereco getEnderecoEntrega() {
        return enderecoEntrega;
    }
    public void setEnderecoEntrega(Endereco enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }
    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
        if (pagamento != null) {
            pagamento.setPedido(this);
        }
    }

    public String getObservacao() {
        return observacao;
    }
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    // ---------- HELPERS DE COMPOSIÇÃO ----------

    public void addItem(ItemPedido item) {
        if (item == null) return;
        item.setPedido(this);
        itens.add(item);
        recalcTotal();
    }

    public void removeItem(ItemPedido item) {
        if (item == null) return;
        itens.remove(item);
        item.setPedido(null);
        recalcTotal();
    }

    public void recalcTotal() {
        this.total = itens.stream()
                .map(ItemPedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
