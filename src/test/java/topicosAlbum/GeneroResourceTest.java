package topicosAlbum;

import org.hamcrest.CoreMatchers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import jakarta.inject.Inject;
import topicosAlbum.dto.GeneroDTO;
import topicosAlbum.dto.GeneroResponseDTO;
import topicosAlbum.exception.ValidationException;
import topicosAlbum.service.GeneroService;

@QuarkusTest
class GeneroResourceTest {

    @Inject
    GeneroService generoService;

    // ==========================================================
    // JWT Helper
    // ==========================================================
    private RequestSpecification admin() {
        return RestAssured.given()
                .header("Authorization", "Bearer " + TokenUtils.getAdminToken());
    }

    // -------------------------------------------------------
    // CRUD BÁSICO
    // -------------------------------------------------------

    @Test
    void buscarTodosGeneros_deveRetornar200() {
        admin()
            .when()
                .get("/generos")
            .then()
                .statusCode(200);
    }

    @Test
    void incluirGenero_deveRetornar201EBodyCorreto() {
        GeneroDTO dto = new GeneroDTO(
            "Indie Rock Teste",
            "Gênero alternativo usado em testes automatizados"
        );

        admin()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post("/generos")
        .then()
            .statusCode(201)
            .body(
                "id",          CoreMatchers.notNullValue(),
                "nomeGenero",  CoreMatchers.is("Indie Rock Teste"),
                "descricao",   CoreMatchers.is("Gênero alternativo usado em testes automatizados")
            );
    }

    @Test
    void alterarGenero_deveAtualizarDadosCorretamente() {
        // cria um gênero inicial via service
        GeneroDTO dto = new GeneroDTO(
            "Genero Original",
            "Descrição original"
        );
        GeneroResponseDTO criado = generoService.create(dto);
        Long id = criado.id();

        GeneroDTO dtoUpdate = new GeneroDTO(
            "Genero Atualizado",
            "Descrição atualizada"
        );

        admin()
            .contentType(ContentType.JSON)
            .body(dtoUpdate)
        .when()
            .put("/generos/" + id)
        .then()
            .statusCode(204);

        // valida via service
        GeneroResponseDTO aposUpdate = generoService.findById(id);

        assertEquals(dtoUpdate.nomeGenero(), aposUpdate.nomeGenero());
        assertEquals(dtoUpdate.descricao(),  aposUpdate.descricao());
    }

    @Test
    void apagarGenero_deveRemoverComSucesso() {
        GeneroDTO dto = new GeneroDTO(
            "Genero Para Apagar",
            "Será removido no teste"
        );

        GeneroResponseDTO criado = generoService.create(dto);
        Long id = criado.id();

        admin()
        .when()
            .delete("/generos/" + id)
        .then()
            .statusCode(204);

        // após deletar, o service deve lançar ValidationException
        assertThrows(ValidationException.class, () -> generoService.findById(id));
    }

    // -------------------------------------------------------
    // CONSULTAS
    // -------------------------------------------------------

    @Test
    void buscarPorNome_deveRetornarGeneroKPop() {
        admin()
        .when()
            .get("/generos/search/K-Pop")
        .then()
            .statusCode(200)
            .body("[0].nomeGenero", CoreMatchers.is("K-Pop"));
    }

    @Test
    void buscarPorAlbum_deveRetornarGenerosDoAlbum1() {
        admin()
        .when()
            .get("/generos/album/1")
        .then()
            .statusCode(200)
            .body("nomeGenero", CoreMatchers.hasItems("K-Pop", "Hip-Hop"));
    }
}
