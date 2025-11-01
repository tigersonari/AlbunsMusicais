package topicosAlbum.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Composicao extends DefaultEntity {

    private LocalDate data; 

    @ManyToMany
    @JoinTable(
    name = "composicao_projeto",
    joinColumns = @JoinColumn(name = "idComposicao"),
    inverseJoinColumns = @JoinColumn(name = "idProjetoMusical"))
    private List<ProjetoMusical> projetoMusical = new ArrayList<>();// artista ou grupo responsável pela composição
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
