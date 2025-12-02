package topicosAlbum.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = Shape.OBJECT)
public enum Perfil {
    ADM(1, "Administrador"), 
    USER(2, "Usuario");

    @JsonProperty("id")
    public final Long ID;

    @JsonProperty("nome")
    public final String NOME;

    Perfil(long id, String nome) {
        this.ID = id;
        this.NOME = nome;
    }

    public static Perfil valueOf(Long id) {
        for (Perfil perfil : Perfil.values()) {
            if (id == perfil.ID)
                return perfil;
        }
        return null;
    }
}