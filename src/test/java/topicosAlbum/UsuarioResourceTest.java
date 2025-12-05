package topicosAlbum;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.specification.RequestSpecification;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.HttpHeaders;
import topicosAlbum.service.UsuarioService;

@QuarkusTest
class UsuarioResourceTest {

    @Inject
    UsuarioService usuarioService;

    // ---------------- HELPERS ----------------
    // os helpers servem 

    private RequestSpecification admin() {
        String token = TokenUtils.getAdminToken();
        return given().header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }

    private RequestSpecification user() {
        String token = TokenUtils.getUserToken();
        return given().header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }

    

    // ---------------- TESTES ----------------

    @Test
    @DisplayName("GET /usuarios - deve retornar lista de usuários (ADM)")
    void buscarTodos_deveRetornar200() {
        admin()
            .when()
                .get("/usuarios")
            .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(2));
    }

    @Test
    @DisplayName("GET /usuarios/{id} - deve retornar admin (id=1)")
    void buscarPorId_deveRetornarAdmin() {
        admin()
            .when()
                .get("/usuarios/1")
            .then()
                .statusCode(200)
                .body("id", is(1),
                      "login", is("admin"));
    }

    @Test
    @DisplayName("GET /usuarios/find/login/{login} - deve retornar usuário 'user'")
    void buscarPorLogin_deveRetornarUser() {
        admin()
            .when()
                .get("/usuarios/find/login/user")
            .then()
                .statusCode(200)
                .body("login", is("user"));
    }

    @Test
    @DisplayName("POST /usuarios/cadastro - deve cadastrar novo usuário (USER autenticado)")
    void cadastrar_deveRetornar201EBodyCorreto() {
        String loginUnico = "user_test_" + System.currentTimeMillis();

        String json = """
        {
          "login": "%s",
          "senha": "123456",
          "idPerfil": 2
        }
        """.formatted(loginUnico);

        user()
            .contentType("application/json")
            .body(json)
        .when()
            .post("/usuarios/cadastro")
        .then()
            .statusCode(201)
            .body("id", notNullValue(),
                  "login", is(loginUnico));
    }

    @Test
    @DisplayName("GET /usuarios/{id} - deve retornar 404 quando usuário não existe")
    void buscarPorId_inexistenteDeveRetornar404() {
        admin()
            .when()
                .get("/usuarios/9999")
            .then()
                .statusCode(404);
    }
}
