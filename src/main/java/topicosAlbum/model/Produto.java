package topicosAlbum.model;


import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "produto")
public class Produto extends DefaultEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id", nullable = false, unique = true)
    private Album album;

    @Column(name = "preco", precision = 19, scale = 4, nullable = false)
    private BigDecimal preco;

    public Album getAlbum() {
         return album;
         }
    public void setAlbum(Album album) { 
        this.album = album; 
    }

    public BigDecimal getPreco() { 
        return preco; 
    }
    public void setPreco(BigDecimal preco) {
         this.preco = preco;
         }
}
