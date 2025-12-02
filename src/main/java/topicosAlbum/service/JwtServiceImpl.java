package topicosAlbum.service;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import topicosAlbum.model.Perfil;

@ApplicationScoped
public class JwtServiceImpl implements JwtService {

    private static final Duration EXPIRATION_TIME = Duration.ofHours(24);

    @Override
    public String generateJwt(String login, Perfil perfil) {

        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("Login não pode ser vazio ao gerar JWT");
        }

        if (perfil == null) {
            throw new IllegalArgumentException("Perfil não pode ser nulo ao gerar JWT");
        }

        Instant expiryDate = Instant.now().plus(EXPIRATION_TIME);

        Set<String> roles = new HashSet<>();
        roles.add(perfil.name()); // ADM, USER...

        return Jwt.issuer("albuns-jwt")     // deve bater com application.properties
                .subject(login)            // sub
                .upn(login)                // user principal
                .groups(roles)             // roles para @RolesAllowed
                .expiresAt(expiryDate)     // expiração
                .claim("perfil", perfil.name())
                .sign();                   // assina com privateKey.pem
    }
}
