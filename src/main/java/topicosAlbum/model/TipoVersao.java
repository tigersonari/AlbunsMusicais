package topicosAlbum.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = Shape.OBJECT)
public enum TipoVersao {
    ORIGINAL(1, "Original"), 
    REMIX(2, "Remix"), 
    ACUSTICO(3, "Acustico"), 
    LIVE(4, "Live"), 
    INSTRUMENTAL(5, "Instrumental"),
    COVER(6, "Cover"),
    REMASTERED(7, "Remastered");

    @JsonProperty("id")
    public final Long ID;

    @JsonProperty("nome")
    public final String NOME;

    TipoVersao(long id, String nome) {
        this.ID = id;
        this.NOME = nome;
    }

    public static TipoVersao valueOf(Long id) {
        for (TipoVersao tipoVersao : TipoVersao.values()) {
            if (id == tipoVersao.ID)
                return tipoVersao;
        }
        return null;
    }
}