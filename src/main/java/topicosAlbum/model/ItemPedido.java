package topicosAlbum.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "item_pedido")
public class ItemPedido extends DefaultEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(name = "preco_unitario", precision = 19, scale = 4, nullable = false)
    private BigDecimal precoUnitario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    public Produto getProduto() { 
        return produto; 
    }
    public void setProduto(Produto produto) { 
        this.produto = produto; 
    }

    public Integer getQuantidade() { 
        return quantidade;
     }
    public void setQuantidade(Integer quantidade) { 
        this.quantidade = quantidade;
     }

    public BigDecimal getPrecoUnitario() {
         return precoUnitario; 
        }
    public void setPrecoUnitario(BigDecimal precoUnitario) { 
        this.precoUnitario = precoUnitario; 
    }

    public Pedido getPedido() { 
        return pedido; 
    }
    public void setPedido(Pedido pedido) { 
        this.pedido = pedido;
     }

    @Transient
    public BigDecimal getSubtotal() {
        if (precoUnitario == null || quantidade == null)
            return BigDecimal.ZERO;
        return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }
}
