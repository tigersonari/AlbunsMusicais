package topicosAlbum.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Composicao extends DefaultEntity {

    private LocalDate data; 

    @OneToMany /* pois uma composição pode ser feita por muitos artistas ou grupos */
    @JoinColumn(name = "projeto_participante_id")
    private List<ProjetoMusical> projetoMusical = new ArrayList<>(); // artista ou grupo responsável pela composição
    /*lista todos os artistas ou grupos musicais que compuseram a faixa */

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public List<ProjetoMusical> getProjetoMusical() {
        return projetoMusical;
    }

    public void setProjetoMusical(List<ProjetoMusical> projetoMusical) {
        this.projetoMusical = projetoMusical;
    }

    


}
