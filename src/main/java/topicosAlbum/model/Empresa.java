package topicosAlbum.model;

import jakarta.persistence.Entity;

@Entity
public class Empresa extends DefaultEntity{
    
    private String nomeEmpresa;
    private String cnpj;
    private String localizacao;
    private String contato;

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }
    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }
    public String getCnpj() {
        return cnpj;
    }
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    public String getLocalizacao() {
        return localizacao;
    }
    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }
    public String getContato() {
        return contato;
    }
    public void setContato(String contato) {
        this.contato = contato;
    }


    

}


