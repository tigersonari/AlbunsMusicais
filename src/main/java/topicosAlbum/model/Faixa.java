package topicosAlbum.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Faixa extends DefaultEntity {


    private String titulo;
    private int numeroFaixa;
    private double duracao; // em minutos
    private String idioma;

   /*  @ManyToOne
    @JoinColumn(name = "projeto_principal_id")
    private ProjetoMusical projetoMusical; */ 
    
    /* o álbum já pertence a um projeto musical, não é necessário que a faixa também pertença. 
     * no entanto, deixarei apenas como comentário caso seja necessário alterar futuramente
    */

    @OneToMany // (cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "idParticipacao") 
    private List<Participacao> participacao = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "idComposicao")
    private Composicao composicao;

    @OneToOne
    @JoinColumn(name = "idGenero")
    private Genero genero;

    // enum
    private TipoVersao tipoVersao;

    

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getNumeroFaixa() {
        return numeroFaixa;
    }

    public void setNumeroFaixa(int numeroFaixa) {
        this.numeroFaixa = numeroFaixa;
    }

    public double getDuracao() {
        return duracao;
    }

    public void setDuracao(double duracao) {
        this.duracao = duracao;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public List<Participacao> getParticipacao() {
        return participacao;
    }

    public void setParticipacao(List<Participacao> participacao) {
        this.participacao = participacao;
    }

    public Composicao getComposicao() {
        return composicao;
    }

    public void setComposicao(Composicao composicao) {
        this.composicao = composicao;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public TipoVersao getTipoVersao() {
        return tipoVersao;
    }

    public void setTipoVersao(TipoVersao tipoVersao) {
        this.tipoVersao = tipoVersao;
    }

   
}
