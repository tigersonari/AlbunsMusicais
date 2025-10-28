package topicosAlbum.model;


import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Artista extends ProjetoMusical {

    @Column(nullable = false)
    private String nomeCompleto;
    private LocalDate dataNascimento;
    private String nacionalidade;
    private String nomeArtistico;
    private String funcaoPrincipal;


    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getNomeArtistico() {
        return nomeArtistico;
    }

    public void setNomeArtistico(String nomeArtistico) {
        this.nomeArtistico = nomeArtistico;
    }

    public String getFuncaoPrincipal() {
        return funcaoPrincipal;
    }

    public void setFuncaoPrincipal(String funcaoPrincipal) {
        this.funcaoPrincipal = funcaoPrincipal;
    }
}
