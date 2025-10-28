package topicosAlbum.model;


import jakarta.persistence.Entity;

@Entity
public class Genero extends DefaultEntity {
      
    private String nomeGenero;
    private String descricao;

    
    public String getNomeGenero() {
        return nomeGenero;
    }
    public void setNomeGenero(String nomeGenero) {
        this.nomeGenero = nomeGenero;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

   

}
