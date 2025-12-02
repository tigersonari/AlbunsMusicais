package topicosAlbum.service;

import topicosAlbum.model.Perfil;

public interface JwtService {

    /**
     * gera um JWT com base no usuário autenticado.
     *
     * @param usuario usuário autenticado
     * @return token JWT assinado
     */
    String generateJwt(String usuario, Perfil perfil);
}
