package topicosAlbum.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Producao extends DefaultEntity{
    
    private String produtor;
    private String engenheiroGravacao;
    private String engenheiroMixagem;
    private String engenheiroMasterizacao;
    private LocalDate dataProducao;

    @OneToOne
    @JoinColumn(name = "idEmpresa")
    private Empresa empresa; // empresa responsável pela produção do álbum


    public String getProdutor() {
        return produtor;
    }

    public void setProdutor(String produtor) {
        this.produtor = produtor;
    }

    public String getEngenheiroGravacao() {
        return engenheiroGravacao;
    }

    public void setEngenheiroGravacao(String engenheiroGravacao) {
        this.engenheiroGravacao = engenheiroGravacao;
    }

    public String getEngenheiroMixagem() {
        return engenheiroMixagem;
    }

    public void setEngenheiroMixagem(String engenheiroMixagem) {
        this.engenheiroMixagem = engenheiroMixagem;
    }

    public String getEngenheiroMasterizacao() {
        return engenheiroMasterizacao;
    }

    public void setEngenheiroMasterizacao(String engenheiroMasterizacao) {
        this.engenheiroMasterizacao = engenheiroMasterizacao;
    }

    public LocalDate getDataProducao() {
        return dataProducao;
    }

    public void setDataProducao(LocalDate dataProducao) {
        this.dataProducao = dataProducao;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    

}


