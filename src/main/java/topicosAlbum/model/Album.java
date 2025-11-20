package topicosAlbum.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // para evitar erros de serialização com lazy loading
public class Album extends DefaultEntity {
      
    private String titulo;
    private LocalDate lancamento;
    private String descricao;

    @OneToOne
    @JoinColumn(name = "idProducao")
    private Producao producao;

    /*enum */
    private Formato formato;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "idAlbum") // FK vai pra tabela de Avaliacao
    private List<AvaliacaoProfissional> avaliacaoProfissional = new ArrayList<>();

    /*lista todas as avaliações que o album recebeu */

    /*@OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Faixa> faixas = new ArrayList<>();*/
    
   // álbum pode ter múltiplos artistas principais
    @ManyToMany
    @JoinTable(
        name = "album_projeto_musical",
        joinColumns = @JoinColumn(name = "idAlbum"),
        inverseJoinColumns = @JoinColumn(name = "idProjetoMusical")
    )
    private List<ProjetoMusical> projetoMusical = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "album_genero",
        joinColumns = @JoinColumn(name = "idAlbum"),
        inverseJoinColumns = @JoinColumn(name = "idGenero")
    )
    private List<Genero> generos = new ArrayList<>();
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

    public List<AvaliacaoProfissional> getAvaliacaoProfissional() {
        return avaliacaoProfissional;
    }

    public void setAvaliacaoProfissional(List<AvaliacaoProfissional> avaliacaoProfissional) {
        this.avaliacaoProfissional = avaliacaoProfissional;
    }

    /*public List<Faixa> getFaixa() {
        return faixas;
    }

    public void setFaixas(List<Faixa> faixas) {
        this.faixas = faixas;
    }*/

    public List<ProjetoMusical> getProjetoMusical() {
        return projetoMusical;
    }

    public void setProjetoMusical(List<ProjetoMusical> projetoMusical) {
        this.projetoMusical = projetoMusical;
    }

    public List<Genero> getGeneros() {
        return generos;
    }

    public void setGeneros(List<Genero> generos) {
        this.generos = generos;
    }

    

}
