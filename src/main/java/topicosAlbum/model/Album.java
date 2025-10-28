package topicosAlbum.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Album extends DefaultEntity {
      
    private String titulo;
    private LocalDate lancamento;
    private String descricao;

    @OneToOne
    @JoinColumn(name = "idProducao")
    private Producao producao;

    //enum
    private Formato formato;

    @OneToMany
    @JoinColumn(name = "idAvaliacaoProfissional")
    private List<AvaliacaoProfissional> avaliacaoProfissional = new ArrayList<>();
    /*lista todas as avaliações que o album recebeu */

    @OneToMany
    @JoinColumn(name = "idFaixa")
    private Faixa faixa;

    @ManyToMany
    @JoinColumn(name = "idProjetoMusical")
    private ProjetoMusical projetoMusical;
    /*pensar se deve ser colocado como um arraylist, já que existem álbuns colaborativos,
     * exemplo: "Watch the Throne" do Jay-Z com Kanye West
     */

     @OneToMany
     @JoinColumn(name = "idGenero")
     private List<Genero> genero = new ArrayList<>();
     /*pois apesar de faixa ter somente um gênero, o álbum que é composto por muitas
      * faixas pode ser classificado em mais de um gênero devido à diversidade das faixas.
      isso é necessário, pois em uma query de álbuns por gênero, o álbum será listado corretamente com 
      base nos gêneros de suas faixas. exemplo: um álbum com faixas de rock e pop pode ser classificado
      em ambos os gêneros.
      */

    

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDate getLancamento() {
        return lancamento;
    }

    public void setLancamento(LocalDate lancamento) {
        this.lancamento = lancamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Producao getProducao() {
        return producao;
    }

    public void setProducao(Producao producao) {
        this.producao = producao;
    }

    public Formato getFormato() {
        return formato;
    }

    public void setFormato(Formato formato) {
        this.formato = formato;
    }

    public Faixa getFaixa() {
        return faixa;
    }

    public void setFaixa(Faixa faixa) {
        this.faixa = faixa;
    }

    public ProjetoMusical getProjetoMusical() {
        return projetoMusical;
    }

    public void setProjetoMusical(ProjetoMusical projetoMusical) {
        this.projetoMusical = projetoMusical;
    }

    public List<AvaliacaoProfissional> getAvaliacaoProfissional() {
        return avaliacaoProfissional;
    }

    public void setAvaliacaoProfissional(List<AvaliacaoProfissional> avaliacaoProfissional) {
        this.avaliacaoProfissional = avaliacaoProfissional;
    }

    public List<Genero> getGenero() {
        return genero;
    }
    
    public void setGenero(List<Genero> genero) {
        this.genero = genero;
    }

}
