package topicosAlbum;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import topicosAlbum.dto.GrupoMusicalDTO;
import topicosAlbum.dto.GrupoMusicalResponseDTO;
import topicosAlbum.exception.ValidationException;
import topicosAlbum.service.GrupoMusicalService;

@QuarkusTest
class GrupoMusicalResourceTest {

    @Inject
    GrupoMusicalService grupoService;

    @Test
    void buscarTodos_deveRetornar200() {
        RestAssured.given()
            .when()
                .get("/grupos-musicais")
            .then()
                .statusCode(200);
    }

    @Test
    void incluirGrupo_deveRetornar201EBodyCorreto() {
        GrupoMusicalDTO dto = new GrupoMusicalDTO(
            "Novo Grupo Teste",
            LocalDate.of(2020, 1, 1),
            null,
            1L,                  // HY Entertainment
            List.of(1L, 2L)      // RM e Suga
        );

        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post("/grupos-musicais")
        .then()
            .statusCode(201)
            .body(
                "id", notNullValue(),
                "nomeGrupo", containsString("Novo Grupo Teste"),
                "empresa.id", org.hamcrest.CoreMatchers.is(1),
                "membros.size()", org.hamcrest.CoreMatchers.is(2)
            );
    }

    @Test
    void buscarPorNome_deveRetornarBTS() {
        RestAssured.given()
            .when()
                .get("/grupos-musicais/nome/BTS")
            .then()
                .statusCode(200)
                .body("[0].nomeGrupo", containsString("BTS"));
    }

    @Test
    void buscarPorEmpresa_deveConterBTSNaHYEntertainment() {
        RestAssured.given()
            .when()
                .get("/grupos-musicais/empresa/1")
            .then()
                .statusCode(200)
                .body("nomeGrupo", hasItem("BTS"));
    }

    @Test
    void buscarAtivos_deveConterBtsETwice() {
        RestAssured.given()
            .when()
                .get("/grupos-musicais/ativos")
            .then()
                .statusCode(200)
                .body("nomeGrupo", hasItems("BTS", "Twice"));
    }

    @Test
    void buscarMembrosDeBTS_deveRetornarRMESuga() {
        RestAssured.given()
            .when()
                .get("/grupos-musicais/4/membros") // 4 = BTS no import-dev.sql
            .then()
                .statusCode(200)
                .body("nomeArtistico", hasItems("RM", "Suga"));
    }

    @Test
    void alterarGrupo_deveAtualizarDadosCorretamente() {
        // cria um grupo via service para ter um ID seguro
        GrupoMusicalDTO dto = new GrupoMusicalDTO(
            "Grupo Original",
            LocalDate.of(2018, 1, 1),
            null,
            1L,
            List.of(1L, 2L)
        );

        GrupoMusicalResponseDTO criado = grupoService.create(dto);

        GrupoMusicalDTO dtoUpdate = new GrupoMusicalDTO(
            "Grupo Atualizado",
            LocalDate.of(2019, 2, 2),
            LocalDate.of(2020, 3, 3),
            1L,
            List.of(1L, 2L)
        );

        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(dtoUpdate)
        .when()
            .put("/grupos-musicais/" + criado.id())
        .then()
            .statusCode(204);

        GrupoMusicalResponseDTO atualizado = grupoService.findById(criado.id());

        assertEquals(dtoUpdate.nomeGrupo(), atualizado.nomeGrupo());
        assertEquals(dtoUpdate.dataInicio(), atualizado.dataInicio());
        assertEquals(dtoUpdate.dataTermino(), atualizado.dataTermino());
        assertEquals(dtoUpdate.idEmpresa(), atualizado.empresa().id());
        assertEquals(dtoUpdate.idsArtistas().size(), atualizado.membros().size());
    }

    @Test
    void apagarGrupo_deveRemoverComSucesso() {
        GrupoMusicalDTO dto = new GrupoMusicalDTO(
            "Grupo Para Deletar",
            LocalDate.of(2020, 5, 5),
            null,
            1L,
            List.of(1L, 2L)
        );

        GrupoMusicalResponseDTO criado = grupoService.create(dto);

        RestAssured.given()
            .when()
                .delete("/grupos-musicais/" + criado.id())
            .then()
                .statusCode(204);

        assertThrows(
            ValidationException.class,
            () -> grupoService.findById(criado.id())
        );
    }
}
