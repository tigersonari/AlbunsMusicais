package topicosAlbum.service;


public interface HashService {
    
    /**
     * gera o hash da senha em formato Base64 usando PBKDF2WithHmacSHA512.
     * 
     * @param senha senha em texto puro
     * @return hash em Base64
     */
    String getHashSenha(String senha);
}
