package topicosAlbum.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;

@Entity
public class GrupoMusical extends Artista{
    
    private String nomeGrupo;
    private int numeroIntegrantes;
    private String generoMusical; // alterar para ENUM e adicionar como FK
    private String info; // informações adicionais sobre o grupo
    private String membros; // depois colocar como (List<ArtistaSolo>) → relação com artistas solos que compõem o grupo
    private LocalDate dataFormacao;
    
    public String getNomeGrupo() {
        return nomeGrupo;
    }

    public void setNomeGrupo(String nomeGrupo) {
        this.nomeGrupo = nomeGrupo;
    }
    public int getNumeroIntegrantes() {
        return numeroIntegrantes;
    }
    public void setNumeroIntegrantes(int numeroIntegrantes) {
        this.numeroIntegrantes = numeroIntegrantes;
    }

    public String getGeneroMusical() {
        return generoMusical;
    }

    public void setGeneroMusical(String generoMusical) {
        this.generoMusical = generoMusical;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getMembros() {
        return membros;
    }

    public void setMembros(String membros) {
        this.membros = membros;
    }

    public LocalDate getDataFormacao() {
        return dataFormacao;
    }

    public void setDataFormacao(LocalDate dataFormacao) {
        this.dataFormacao = dataFormacao;
    }
    
}
