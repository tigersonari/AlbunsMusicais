package topicosAlbum;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import io.restassured.specification.RequestSpecification;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.HttpHeaders;
import topicosAlbum.exception.ValidationException;
import topicosAlbum.service.EnderecoService;

@QuarkusTest
class EnderecoResourceTest {

    @Inject
    EnderecoService enderecoService;

    private RequestSpecification admin() {
        String token = TokenUtils.getAdminToken();
        return given().header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }

    private RequestSpecification user() {
        String token = TokenUtils.getUserToken();
        return given().header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }

    // helper para criar um endereço via API e retornar o id
    private Long criarEnderecoParaUser2() {
        String json = """
        {
          "rua": "Rua Teste",
          "numero": "123",
          "complemento": "Ap 10",
          "bairro": "Centro",
          "cidade": "Seoul",
          "uf": "SP",
          "cep": "12345-678",
          "idUsuario": 2
        }
        """;

        Number idNum =
            user()
                .contentType(JSON)
                .body(json)
            .when()
                .post("/enderecos")
            .then()
                .statusCode(201)
                .body("id", notNullValue(),
                      "rua", is("Rua Teste"),
                      "cidade", is("Seoul"))
                .extract()
                .path("id");

        return idNum.longValue();
    }

    @Test
    @DisplayName("POST /enderecos - deve criar endereço para usuário 2")
    void incluirEndereco_deveRetornar201EBodyCorreto() {
        criarEnderecoParaUser2();
    }

    @Test
    @DisplayName("GET /enderecos/usuario/{idUsuario} - deve listar endereços do usuário")
    void buscarPorUsuario_deveRetornarLista() {
        Long idEndereco = criarEnderecoParaUser2();

        admin()
            .when()
                .get("/enderecos/usuario/2")
            .then()
                .statusCode(200)
                .body("id", hasItem(idEndereco.intValue()));
    }

    @Test
    @DisplayName("PUT /enderecos/{id} - deve atualizar dados corretamente")
    void alterarEndereco_deveAtualizarDados() {
        Long idEndereco = criarEnderecoParaUser2();

        String jsonUpdate = """
        {
          "rua": "Rua Atualizada",
          "numero": "999",
          "complemento": "Casa",
          "bairro": "Bairro Novo",
          "cidade": "Busan",
          "uf": "RJ",
          "cep": "99999-999",
          "idUsuario": 2
        }
        """;

        user()
            .contentType(JSON)
            .body(jsonUpdate)
        .when()
            .put("/enderecos/" + idEndereco)
        .then()
            .statusCode(204);

        admin()
            .when()
                .get("/enderecos/" + idEndereco)
            .then()
                .statusCode(200)
                .body("rua", is("Rua Atualizada"),
                      "cidade", is("Busan"),
                      "numero", is("999"));
    }

    @Test
    @DisplayName("DELETE /enderecos/{id} - deve remover com sucesso")
    void apagarEndereco_deveRemoverComSucesso() {
        Long idEndereco = criarEnderecoParaUser2();

        user()
            .when()
                .delete("/enderecos/" + idEndereco)
            .then()
                .statusCode(204);

        assertThrows(ValidationException.class,
            () -> enderecoService.findById(idEndereco));
    }
}
