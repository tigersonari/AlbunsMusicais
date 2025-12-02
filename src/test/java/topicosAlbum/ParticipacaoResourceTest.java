package topicosAlbum;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import topicosAlbum.dto.ParticipacaoDTO;
import topicosAlbum.dto.ParticipacaoResponseDTO;
import topicosAlbum.exception.ValidationException;
import topicosAlbum.service.ParticipacaoService;

@QuarkusTest
class ParticipacaoResourceTest {

    @Inject
    ParticipacaoService participacaoService;

    // ------------------- CRUD BÁSICO -------------------

    @Test
    @DisplayName("GET /participacoes - deve retornar 200")
    void buscarTodasParticipacoes_deveRetornar200() {
        given()
            .when()
                .get("/participacoes")
            .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("POST /participacoes - deve criar nova participação com sucesso")
    void incluirParticipacao_deveRetornar201EBodyCorreto() {
        // projetoMusical 3 = Taeyeon (do import-dev.sql)
        ParticipacaoDTO dto = new ParticipacaoDTO(
            "Feat Especial",
            true,
            List.of(3L)
        );

        given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post("/participacoes")
        .then()
            .statusCode(201)
            .body("id", notNullValue(),
                  "papel", is("Feat Especial"),
                  "destaque", is(true),
                  "participantes.size()", is(1),
                  "participantes[0].id", is(3));
    }

    @Test
    @DisplayName("PUT /participacoes/{id} - deve atualizar dados corretamente")
    void alterarParticipacao_deveAtualizarDadosCorretamente() {
        // cria via service para ter um ID conhecido
        ParticipacaoDTO dtoCriacao = new ParticipacaoDTO(
            "Participação Inicial",
            false,
            List.of(1L) // RM
        );

        ParticipacaoResponseDTO criada = participacaoService.create(dtoCriacao);

        ParticipacaoDTO dtoUpdate = new ParticipacaoDTO(
            "Participação Atualizada",
            true,
            List.of(2L) // Suga
        );

        given()
            .contentType(ContentType.JSON)
            .body(dtoUpdate)
        .when()
            .put("/participacoes/" + criada.id())
        .then()
            .statusCode(204);

        ParticipacaoResponseDTO atualizada = participacaoService.findById(criada.id());

        assertEquals(dtoUpdate.papel(), atualizada.papel());
        assertEquals(dtoUpdate.destaque(), atualizada.destaque());
        assertEquals(1, atualizada.participantes().size());
        assertEquals(2L, atualizada.participantes().get(0).id());
    }

    @Test
    @DisplayName("DELETE /participacoes/{id} - deve remover com sucesso")
    void apagarParticipacao_deveRemoverComSucesso() {
        ParticipacaoDTO dtoCriacao = new ParticipacaoDTO(
            "Participação para Remover",
            false,
            List.of(1L)
        );

        ParticipacaoResponseDTO criada = participacaoService.create(dtoCriacao);

        given()
            .when()
                .delete("/participacoes/" + criada.id())
            .then()
                .statusCode(204);

        // Serviço lança ValidationException se não encontrar
        assertThrows(ValidationException.class,
            () -> participacaoService.findById(criada.id()));
    }

    // ------------------- CONSULTAS ESPECÍFICAS -------------------

    @Test
    @DisplayName("GET /participacoes/{id} - deve retornar participação existente")
    void buscarPorId_deveRetornarParticipacaoExistente() {
        // do import-dev.sql: participacao id=1, papel='Feat', destaque=false, projeto=Taeyeon (id=3)
        given()
            .when()
                .get("/participacoes/1")
            .then()
                .statusCode(200)
                .body("id", is(1),
                      "papel", is("Feat"),
                      "destaque", is(false),
                      "participantes[0].id", is(3),
                      "participantes[0].nomeArtistico", containsString("Taeyeon"));
    }

    @Test
    @DisplayName("GET /participacoes/faixa/{idFaixa} - deve retornar participações da faixa 2 (Black Swan)")
    void buscarPorFaixa_deveRetornarParticipacoesDaFaixa() {
        // import-dev.sql: faixa 2 = Black Swan, com participacao id=1 (Taeyeon)
        given()
            .when()
                .get("/participacoes/faixa/2")
            .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(1),
                      "[0].papel", is("Feat"),
                      "[0].participantes[0].nomeArtistico", containsString("Taeyeon"));
    }

    @Test
    @DisplayName("GET /participacoes/projeto/{idProjetoMusical} - deve retornar participações da Taeyeon (id=3)")
    void buscarPorProjeto_deveRetornarParticipacoesDoProjeto() {
        given()
            .when()
                .get("/participacoes/projeto/3")
            .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(1),
                      "[0].participantes[0].id", is(3));
    }

    @Test
    @DisplayName("GET /participacoes/papel/{papel} - deve retornar participações com papel 'Feat'")
    void buscarPorPapel_deveRetornarParticipacoesComPapelFeat() {
        given()
            .when()
                .get("/participacoes/papel/Feat")
            .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(1),
                      "[0].papel", is("Feat"));
    }

    @Test
    @DisplayName("GET /participacoes/destaque - deve retornar somente participações com destaque=true")
    void buscarPrincipais_deveRetornarSomenteParticipacoesComDestaqueTrue() {
        // cria uma participação em destaque antes de consultar
        ParticipacaoDTO dto = new ParticipacaoDTO(
            "Participação Principal",
            true,
            List.of(4L) // BTS
        );

        ParticipacaoResponseDTO criada = participacaoService.create(dto);
        Long idCriada = criada.id();

        given()
            .when()
                .get("/participacoes/destaque")
            .then()
                .statusCode(200)
                // sem withArgs: interpolamos o id direto na expressão GPath
                .body("find { it.id == " + idCriada + " }.destaque", is(true));
    }

    @Test
    @DisplayName("GET /participacoes/album/{idAlbum} - deve retornar participações do álbum 2 (Twice / Justin)")
    void buscarPorAlbum_deveRetornarParticipacoesDoAlbum() {
        // import-dev.sql: album 2 tem faixa 4 com participacao id=2 (Justin Bieber)
        given()
            .when()
                .get("/participacoes/album/2")
            .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(1),
                      "[0].papel", is("Feat"),
                      "[0].participantes[0].nomeArtistico", containsString("Justin Bieber"));
    }
}
