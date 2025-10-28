package topicosAlbum.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Participacao extends DefaultEntity {

    private String papel; // feat, colab, remix...
    private boolean destaque; // artista, grupo. se o participante é um artista principal (true) ou um artista de apoio

    @ManyToOne
    @JoinColumn(name = "projeto_participante_id")
    private List<ProjetoMusical> projetoMusical = new ArrayList<>(); // artista ou grupo
    /*lista todos os artistas ou grupos musicais que participaram da faixa, deve ser lista porque uma faixa
     * pode ter mais de uma participação (ex: música com dois feats)
    */

    public String getPapel() {
        return papel;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }

    public boolean isDestaque() {
        return destaque;
    }

    public void setDestaque(boolean destaque) {
        this.destaque = destaque;
    }

    public List<ProjetoMusical> getProjetoMusical() {
        return projetoMusical;
    }

    public void setProjetoMusical(List<ProjetoMusical> projetoMusical) {
        this.projetoMusical = projetoMusical;
    }

    


}
