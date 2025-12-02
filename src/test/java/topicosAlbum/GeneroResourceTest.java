package topicosAlbum;

import org.hamcrest.CoreMatchers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import topicosAlbum.dto.GeneroDTO;
import topicosAlbum.dto.GeneroResponseDTO;
import topicosAlbum.exception.ValidationException;
import topicosAlbum.service.GeneroService;

@QuarkusTest
class GeneroResourceTest {

    @Inject
    GeneroService generoService;

    // -------------------------------------------------------
    // CRUD BÁSICO
    // -------------------------------------------------------

    @Test
    void buscarTodosGeneros_deveRetornar200() {
        RestAssured.given()
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

        RestAssured.given()
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
        // cria um gênero inicial via service (não mexe nos seeds)
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

        RestAssured.given()
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

        RestAssured.given()
        .when()
            .delete("/generos/" + id)
        .then()
            .statusCode(204);

        // após deletar, o service deve lançar ValidationException ao buscar
        assertThrows(ValidationException.class, () -> generoService.findById(id));
    }

    // -------------------------------------------------------

    @Test
    void buscarPorNome_deveRetornarGeneroKPop() {
        RestAssured.given()
        .when()
            .get("/generos/search/K-Pop")
        .then()
            .statusCode(200)
            .body("[0].nomeGenero", CoreMatchers.is("K-Pop"));
    }

    @Test
    void buscarPorAlbum_deveRetornarGenerosDoAlbum1() {
        RestAssured.given()
        .when()
            .get("/generos/album/1")
        .then()
            .statusCode(200)
            // deve conter pelo menos K-Pop e Hip-Hop
            .body("nomeGenero", CoreMatchers.hasItems("K-Pop", "Hip-Hop"));
    }
}
