package topicosAlbum.model;

import jakarta.persistence.Entity;
import java.time.LocalDate;

@Entity
public class ArtistaSolo extends Artista{
    
    private String nomeArtistico;
    private LocalDate dataInicioCarreira;

    public String getNomeArtistico() {
        return nomeArtistico;
    }

    public void setNomeArtistico(String nomeArtistico) {
        this.nomeArtistico = nomeArtistico;
    }
    public LocalDate getDataInicioCarreira() {
        return dataInicioCarreira;
    }
    public void setDataInicioCarreira(LocalDate dataInicioCarreira) {
        this.dataInicioCarreira = dataInicioCarreira;
    }
}
