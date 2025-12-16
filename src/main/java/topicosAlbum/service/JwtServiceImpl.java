package topicosAlbum.service;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import topicosAlbum.model.Perfil;
import topicosAlbum.model.Usuario;

@ApplicationScoped
public class JwtServiceImpl implements JwtService {

    private static final Duration EXPIRATION_TIME = Duration.ofHours(24);

    /**
     * Agora o JWT recebe o próprio Usuario,
     * permitindo incluir o ID dentro do token.
     */
    @Override
    public String generateJwt(Usuario usuario) {

        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não pode ser nulo ao gerar JWT");
        }

        String login = usuario.getLogin();
        Perfil perfil = usuario.getPerfil();

        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("Login não pode ser vazio ao gerar JWT");
        }

        if (perfil == null) {
            throw new IllegalArgumentException("Perfil não pode ser nulo ao gerar JWT");
        }

        Instant expiryDate = Instant.now().plus(EXPIRATION_TIME);

        Set<String> roles = new HashSet<>();
        roles.add(perfil.name()); // ADM, USER...

        return Jwt.issuer("albuns-jwt")
                .subject(login)
                .upn(login)
                .groups(roles)
                .expiresAt(expiryDate)
                .claim("perfil", perfil.name())
                .claim("idUsuario", usuario.getId())   // 🔥 ESSENCIAL
                .sign();
    }
}
