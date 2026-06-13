package topicosAlbum.resource;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import topicosAlbum.dto.AlterarSenhaDTO;
import topicosAlbum.dto.AtualizarPerfilDTO;
import topicosAlbum.dto.EsqueciSenhaDTO;
import topicosAlbum.dto.RedefinirSenhaDTO;
import topicosAlbum.dto.UsuarioDTO;
import topicosAlbum.dto.UsuarioResponseDTO;
import topicosAlbum.model.Usuario;
import topicosAlbum.service.UsuarioService;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
JsonWebToken jwt;

    private static final Logger LOG = Logger.getLogger(UsuarioResource.class);

    @Inject
    UsuarioService service;

    // ---------------- CADASTRO PÚBLICO ----------------

    @POST
    @Path("/cadastro")
    @PermitAll
    public Response cadastrar(@Valid UsuarioDTO dto) {
        LOG.info(">>> [UsuarioResource] POST /usuarios/cadastro chamado para cadastrar novo usuário");
        return Response
            .status(Status.CREATED)
            .entity(service.create(dto))
            .build();
    }
    
    

    // ---------------- LISTAR TODOS (ADM) ----------------

    @GET
    @RolesAllowed("ADM")
    public Response buscarTodos() {
        LOG.info(">>> [UsuarioResource] GET /usuarios chamado para buscar todos os usuários");
        List<Usuario> usuarios = service.findAll();
        List<UsuarioResponseDTO> dtos = usuarios
            .stream()
            .map(UsuarioResponseDTO::valueOf)
            .toList();

        return Response.ok(dtos).build();
    }

    ///
    @GET
@Path("/me")
@RolesAllowed({"ADM", "USER"})
public Response meusDados() {
    Long idUsuario = Long.valueOf(jwt.getClaim("idUsuario").toString());

    Usuario u = service.findById(idUsuario);

    if (u == null) {
        return Response.status(Status.NOT_FOUND).build();
    }

    return Response.ok(UsuarioResponseDTO.valueOf(u)).build();
}

    // ---------------- BUSCAR POR ID (ADM) ----------------

    @GET
    @Path("/{id}")
    @RolesAllowed("ADM")
    public Response buscarPorId(@PathParam("id") Long id) {
        LOG.info(">>> [UsuarioResource] GET /usuarios/{id} chamado para buscar usuário por id");
        Usuario u = service.findById(id);
        if (u == null)
            return Response.status(Status.NOT_FOUND).build();

        return Response.ok(UsuarioResponseDTO.valueOf(u)).build();
    }

    // ---------------- BUSCAR POR LOGIN (ADM) ----------------

    @GET
    @Path("/find/login/{login}")
    @RolesAllowed("ADM")
    public Response buscarPorLogin(@PathParam("login") String login) {
        LOG.info(">>> [UsuarioResource] GET /usuarios/find/login/{login} chamado para buscar usuário por login");
        Usuario u = service.findByLogin(login);
        if (u == null)
            return Response.status(Status.NOT_FOUND).build();

        return Response.ok(UsuarioResponseDTO.valueOf(u)).build();
    }

    // ---------------- BUSCAR POR LOGIN + SENHA (ADM / DEBUG) ----------------

    @GET
    @Path("/find/auth")
    @RolesAllowed("ADM")
    public Response buscarPorLoginESenha(
        @QueryParam("login") String login,
        @QueryParam("senha") String senha
    ) {
        LOG.info(">>> [UsuarioResource] GET /usuarios/find/auth chamado para buscar usuário por login e senha");
        Usuario u = service.findByLoginAndSenha(login, senha);
        if (u == null)
            return Response.status(Status.NOT_FOUND).build();

        return Response.ok(UsuarioResponseDTO.valueOf(u)).build();
    }

    // ---------------- ALTERAR SENHA (USUÁRIO) ----------------
    @PUT
@Path("/alterar-senha")
@RolesAllowed({"ADM", "USER"})
public Response alterarSenha(@Valid AlterarSenhaDTO dto) {
    Long idUsuario = Long.valueOf(jwt.getClaim("idUsuario").toString());

    service.alterarSenha(idUsuario, dto.senhaAtual(), dto.novaSenha());

    return Response.noContent().build();
}

// ---------------- ATUALIZAR PERFIL (USUÁRIO) ----------------

@PUT
@Path("/perfil")
@RolesAllowed({"ADM", "USER"})
public Response atualizarPerfil(@Valid AtualizarPerfilDTO dto) {
    Long idUsuario = Long.valueOf(jwt.getClaim("idUsuario").toString());

    return Response.ok(
        service.atualizarPerfil(idUsuario, dto)
    ).build();
}


    // ---------------- RECUPERAÇÃO DE SENHA (PÚBLICO) ----------------

    @POST
    @Path("/esqueci-senha")
    @PermitAll
    public Response esqueciSenha(@Valid EsqueciSenhaDTO dto) {
        String token = service.solicitarRecuperacaoSenha(dto.email());

        return Response.ok(
            java.util.Map.of(
                "mensagem", "Token de recuperação gerado. Em produção, ele seria enviado por e-mail.",
                "token", token
            )
        ).build();
    }

    @POST
    @Path("/redefinir-senha")
    @PermitAll
    public Response redefinirSenha(@Valid RedefinirSenhaDTO dto) {
        service.redefinirSenha(dto.token(), dto.novaSenha());

        return Response.ok(
            java.util.Map.of("mensagem", "Senha redefinida com sucesso.")
        ).build();
    }

    // ---------------- PROMOVER USUÁRIO A ADMIN (ADM) ----------------
    @PUT
    @Path("/{id}/promover-adm")
    @RolesAllowed("ADM")
    public Response promoverParaAdmin(@PathParam("id") Long id) {
        service.promoverParaAdmin(id);
        return Response.noContent().build();
    }


}
