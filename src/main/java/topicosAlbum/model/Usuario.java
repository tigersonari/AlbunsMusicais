package topicosAlbum.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Usuario extends DefaultEntity {

    private String nome;

    @Column(unique = true)
    private String login;
    private String senha;
    private String email;
    private String telefone;
    
    private Perfil perfil;

    private String tokenRecuperacaoSenha;
    private java.time.LocalDateTime tokenRecuperacaoExpiracao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    // Getters e setters para token de recuperação de senha
    public String getTokenRecuperacaoSenha() {
    return tokenRecuperacaoSenha;
    }

    public void setTokenRecuperacaoSenha(String tokenRecuperacaoSenha) {
        this.tokenRecuperacaoSenha = tokenRecuperacaoSenha;
    }

    public java.time.LocalDateTime getTokenRecuperacaoExpiracao() {
        return tokenRecuperacaoExpiracao;
    }

    public void setTokenRecuperacaoExpiracao(java.time.LocalDateTime tokenRecuperacaoExpiracao) {
        this.tokenRecuperacaoExpiracao = tokenRecuperacaoExpiracao;
    }
   
    

}