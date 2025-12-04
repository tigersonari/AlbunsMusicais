package topicosAlbum;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItems; // <- só Matchers aqui
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import jakarta.inject.Inject;
import topicosAlbum.dto.AvaliacaoProfissionalDTO;
import topicosAlbum.dto.AvaliacaoProfissionalResponseDTO;
import topicosAlbum.service.AvaliacaoProfissionalService;

@QuarkusTest
public class AvaliacaoProfissionalResourceTest {

    @Inject
    AvaliacaoProfissionalService avaliacaoService;

    /**
     * Helper que aplica o header Authorization usando a sua classe TokenUtils.
     * Usa token admin para testes que fazem escrita.
     */
    private RequestSpecification admin() {
        String token = TokenUtils.getAdminToken();
        return RestAssured.given().header("Authorization", "Bearer " + token);
    }

    // ========== CRUD BÁSICO ==========

    @Test
    void buscarTodos_deveRetornarListaInicial() {
        admin()
        .when()
            .get("/avaliacoes")
        .then()
            .statusCode(200)
            .body("size()", greaterThanOrEqualTo(4)); // 4 do import-dev.sql
    }

    @Test
    void buscarPorId_deveRetornarBillboardMasterpiece() {
        // import-dev.sql: id 1 = Billboard, nota 10, album 1
        admin()
        .when()
            .get("/avaliacoes/{id}", 1L)
        .then()
            .statusCode(200)
            .body("id", is(1),
                  "avaliador", is("Billboard"),
                  "comentario", is("Masterpiece"),
                  "nota", is(10.0f));
    }

    @Test
    void incluirAvaliacao_deveRetornar201EBodyCorreto() {
        AvaliacaoProfissionalDTO dto = new AvaliacaoProfissionalDTO(
            "New Music Critic",
            "Muito bom álbum",
            9.0,
            1L // álbum 1 - MAP OF THE SOUL: 7
        );

        admin()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post("/avaliacoes")
        .then()
            .statusCode(201)
            .body("id", notNullValue(),
                  "avaliador", is("New Music Critic"),
                  "comentario", is("Muito bom álbum"),
                  "nota", is(9.0f),
                  "album.id", is(1),
                  "album.titulo", is("MAP OF THE SOUL: 7"));
    }

    @Test
    void apagarAvaliacao_deveRemoverComSucesso() {
        AvaliacaoProfissionalDTO dto = new AvaliacaoProfissionalDTO(
            "Temporary Reviewer",
            "Avaliação temporária",
            7.5,
            1L
        );

        AvaliacaoProfissionalResponseDTO criada = avaliacaoService.create(dto);

        admin()
        .when()
            .delete("/avaliacoes/{id}", criada.id())
        .then()
            .statusCode(204);

        // depois de deletar, buscar deve disparar ValidationException -> 400
        admin()
        .when()
            .get("/avaliacoes/{id}", criada.id())
        .then()
            .statusCode(400);
    }

    // ========== QUERIES ESPECÍFICAS ==========

    @Test
    void buscarPorAlbum_deveRetornarAvaliacoesDoAlbum1() {
        admin()
        .when()
            .get("/avaliacoes/album/{idAlbum}", 1L)
        .then()
            .statusCode(200)
            .body("size()", greaterThanOrEqualTo(2),
                  "avaliador", hasItems("Billboard", "Rolling Stone"));
    }

    @Test
    void buscarPorAvaliador_deveRetornarAvaliacoesDaBillboard() {
        admin()
        .when()
            .get("/avaliacoes/avaliador/{nome}", "Billboard")
        .then()
            .statusCode(200)
            .body("size()", greaterThanOrEqualTo(1),
                  "[0].avaliador", is("Billboard"),
                  "[0].comentario", is("Masterpiece"));
    }

    @Test
    void buscarPorAlbumENota_deveRetornarApenasNotaExata() {
        // Album 1 tem Billboard (10) e Rolling Stone (9.5)
        admin()
        .when()
            .get("/avaliacoes/album/{idAlbum}/nota/{nota}", 1L, 10.0)
        .then()
            .statusCode(200)
            .body("size()", is(1),
                  "[0].avaliador", is("Billboard"),
                  "[0].nota", is(10.0f));
    }

    @Test
    void buscarPorAlbumENotaMinima_deveRetornarApenasAvaliacoesAcimaDoCorte() {
        // Album 2: NME (8.8), Pitchfork (8.2)
        // Se notaMinima = 8.5, deve vir só NME
        admin()
        .when()
            .get("/avaliacoes/album/{idAlbum}/nota-minima/{nota}", 2L, 8.5)
        .then()
            .statusCode(200)
            .body("size()", is(1),
                  "[0].avaliador", is("NME"),
                  "[0].nota", is(8.8f));
    }

    // ========== CENÁRIO DE ERRO ==========

    @Test
    void incluirAvaliacao_emAlbumInexistente_deveRetornarErroValidacao() {
        AvaliacaoProfissionalDTO dto = new AvaliacaoProfissionalDTO(
            "Critico Fantasma",
            "Não deveria ser criada",
            5.0,
            999L // álbum inexistente
        );

        var response = admin()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post("/avaliacoes")
        .then()
            .statusCode(400)
            .extract()
            .response();

        String details = response.jsonPath().getString("details");
        Assertions.assertTrue(
            details == null || details.contains("Álbum não encontrado"),
            "Deveria indicar problema com álbum inexistente"
        );
    }
}
