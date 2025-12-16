package topicosAlbum.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cartao_salvo")
public class CartaoSalvo extends DefaultEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "nome_titular", nullable = false)
    private String nomeTitular;

    @Column(name = "numero_mascarado", nullable = false)
    private String numeroMascarado; // Ex: **** **** **** 1234

    @Column(name = "ultimos4", nullable = false, length = 4)
    private String ultimos4;

    @Column(name = "validade", nullable = false)
    private String validade; // MM/AA

    @Column(name = "bandeira", nullable = false)
    private String bandeira;

    // GETTERS E SETTERS
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public String getNomeTitular() { return nomeTitular; }
    public void setNomeTitular(String nomeTitular) { this.nomeTitular = nomeTitular; }

    public String getNumeroMascarado() { return numeroMascarado; }
    public void setNumeroMascarado(String numeroMascarado) { this.numeroMascarado = numeroMascarado; }

    public String getUltimos4() { return ultimos4; }
    public void setUltimos4(String ultimos4) { this.ultimos4 = ultimos4; }

    public String getValidade() { return validade; }
    public void setValidade(String validade) { this.validade = validade; }

    public String getBandeira() { return bandeira; }
    public void setBandeira(String bandeira) { this.bandeira = bandeira; }
}
