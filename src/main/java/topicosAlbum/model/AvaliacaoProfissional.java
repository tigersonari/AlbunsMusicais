package topicosAlbum.model;

import jakarta.persistence.Entity;

@Entity
public class AvaliacaoProfissional extends DefaultEntity{
    
    private String avaliador;
    private String comentario;
    private double nota;

    
    public String getAvaliador() {
        return avaliador;
    }
    public void setAvaliador(String avaliador) {
        this.avaliador = avaliador;
    }
    public String getComentario() {
        return comentario;
    }
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    public double getNota() {
        return nota;
    }
    public void setNota(double nota) {
        this.nota = nota;
    }

    

}


