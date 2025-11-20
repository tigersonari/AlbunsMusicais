package topicosAlbum.model;


import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class GrupoMusical extends ProjetoMusical {

    private String nomeGrupo;
    private LocalDate dataInicio;
    private LocalDate dataTermino;

    @ManyToMany /*(fetch = FetchType.LAZY, cascade = CascadeType.ALL)*/
    @JoinTable(
        name = "grupo_musical_artista",
        joinColumns = @JoinColumn(name = "idGrupo"),
        inverseJoinColumns = @JoinColumn(name = "idArtista")
    )
    private List<Artista> membros;


    public String getNomeGrupo() {
        return nomeGrupo;
    }

    public void setNomeGrupo(String nomeGrupo) {
        this.nomeGrupo = nomeGrupo;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(LocalDate dataTermino) {
        this.dataTermino = dataTermino;
    }

    public List<Artista> getMembros() {
        return membros;
    }

    public void setMembros(List<Artista> membros) {
        this.membros = membros;
    }
}
