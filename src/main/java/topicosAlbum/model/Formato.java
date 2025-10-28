package topicosAlbum.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = Shape.OBJECT)
public enum Formato {
    SINGLE(1, "Single"), 
    MIXTAPE(2, "Mixtape"), 
    DELUXE(3, "Deluxe"), 
    EP(4, "Ep"), 
    LONGPLAY(5, "Longplay");

    @JsonProperty("id")
    public final Long ID;

    @JsonProperty("nome")
    public final String NOME;

    Formato(long id, String nome) {
        this.ID = id;
        this.NOME = nome;
    }

    public static Formato valueOf(Long id) {
        for (Formato formato : Formato.values()) {
            if (id == formato.ID)
                return formato;
        }
        return null;
    }
}