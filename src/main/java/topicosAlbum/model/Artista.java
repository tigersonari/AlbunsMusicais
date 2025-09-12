package topicosAlbum.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;

@Entity
public class Artista extends DefaultEntity{
    
    private String nome;
    private String nacionalidade;
    private LocalDate dataNascimento;
    private String instrumentoPrincipal; // ex.: guitarra, voz, bateria
    private String info; // informações adicionais sobre o artista

    /*private Empresa empresa; // FOREIGN KEY -- empresa associada ao artista*/

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getNacionalidade() {
        return nacionalidade;
    }
    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    public String getInstrumentoPrincipal() {
        return instrumentoPrincipal;
    }
    public void setInstrumentoPrincipal(String instrumentoPrincipal) {
        this.instrumentoPrincipal = instrumentoPrincipal;
    }
    public String getInfo() {
        return info;
    }
    public void setInfo(String info) {
        this.info = info;
    }
  //* getter e setter da foreign key */
   /*  public Empresa getEmpresa() {
        return empresa;
    }
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }*/
    
}
