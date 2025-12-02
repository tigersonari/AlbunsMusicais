package topicosAlbum.resource;


import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import topicosAlbum.dto.AuthDTO;
import topicosAlbum.dto.AuthResponseDTO;
import topicosAlbum.model.Usuario;
import topicosAlbum.service.HashService;
import topicosAlbum.service.JwtService;
import topicosAlbum.service.UsuarioService;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    HashService hashService;

    @Inject
    JwtService jwtService;

    @Inject
    UsuarioService usuarioService;

    @POST
    public Response login(@Valid AuthDTO dto) {
        try {
            // 1) gera hash da senha informada
            String hash = hashService.getHashSenha(dto.senha());

            // 2) busca o usuário pelo login + hash da senha
            Usuario usuario = usuarioService.findByLoginAndSenha(dto.login(), hash);

            // 3) se não achou, login inválido
            if (usuario == null) {
    return Response.status(Status.UNAUTHORIZED)
                   .entity("Login ou senha inválidos.")
                   .build();
}


            // 4) gera token JWT com login + perfil
            String token = jwtService.generateJwt(usuario.getLogin(), usuario.getPerfil());

            // 5) monta DTO de resposta com o token
            AuthResponseDTO responseDTO = AuthResponseDTO.valueOf(usuario, token);

            // 6) devolve no corpo + header Authorization (opcional, mas legal)
            return Response.ok(responseDTO)
                           .header("Authorization", "Bearer " + token)
                           .build();

        } catch (RuntimeException e) {
    return Response.status(Status.INTERNAL_SERVER_ERROR)
                   .entity("Erro ao processar autenticação.")
                   .build();
}

    }
}
