package topicosAlbum;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import topicosAlbum.dto.ComposicaoDTO;
import topicosAlbum.dto.ComposicaoResponseDTO;
import topicosAlbum.dto.FaixaDTO;
import topicosAlbum.dto.FaixaResponseDTO;
import topicosAlbum.service.ComposicaoService;
import topicosAlbum.service.FaixaService;

@QuarkusTest
public class FaixaResourceTest {

    @Inject
    FaixaService faixaService;

    @Inject
    ComposicaoService composicaoService;

    // ---------- Helpers ----------

    private ComposicaoResponseDTO criarComposicaoSimples(Long idProjetoMusical) {
        ComposicaoDTO dto = new ComposicaoDTO(
            LocalDate.now(),
            List.of(idProjetoMusical) // ex: 1L = RM
        );
        return composicaoService.create(dto);
    }

    // ---------- CRUD BÁSICO ----------

    @Test
    void buscarTodasFaixas_deveRetornar200() {
        RestAssured.given()
            .when()
                .get("/faixas")
            .then()
                .statusCode(200);
    }

    @Test
    void incluirFaixa_deveCriarNovaFaixaComRelacionamentos() {
        // cria uma composicao nova, não usada pelo import-dev.sql
        ComposicaoResponseDTO composicao = criarComposicaoSimples(1L); // RM

        FaixaDTO dto = new FaixaDTO(
            "Nova Faixa Teste",
            5,
            2.50,
            "Portuguese",
            1L,           // TipoVersao ORIGINAL
            1L,           // Genero K-Pop
            1L,           // Album MAP OF THE SOUL: 7
            composicao.id()
        );

        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post("/faixas")
        .then()
            .statusCode(201)
            .body("id", notNullValue(),
                  "titulo", is("Nova Faixa Teste"),
                  "numeroFaixa", is(5),
                  "idioma", is("Portuguese"),
                  "album.id", is(1),
                  "genero.id", is(1),
                  "composicao.id", is(composicao.id().intValue()));
    }

    @Test
    void alterarFaixa_deveAtualizarDadosCorretamente() {
        // composicao inicial para a faixa
        ComposicaoResponseDTO compOriginal = criarComposicaoSimples(1L);
        // composicao para o update
        ComposicaoResponseDTO compUpdate = criarComposicaoSimples(2L);

        FaixaDTO dto = new FaixaDTO(
            "Faixa Original",
            10,
            3.10,
            "Portuguese",
            1L,
            1L,
            1L,
            compOriginal.id()
        );

        FaixaResponseDTO criada = faixaService.create(dto);

        FaixaDTO dtoUpdate = new FaixaDTO(
            "Faixa Atualizada",
            11,
            3.55,
            "English",
            1L,
            2L,               // muda o gênero
            2L,               // muda o álbum
            compUpdate.id()   // muda a composição
        );

        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(dtoUpdate)
        .when()
            .put("/faixas/" + criada.id())
        .then()
            .statusCode(204);

        // busca de novo e valida alterações
        FaixaResponseDTO atualizada = faixaService.findById(criada.id());
        assertEquals(dtoUpdate.titulo(), atualizada.titulo());
        assertEquals(dtoUpdate.numeroFaixa(), atualizada.numeroFaixa());
        assertEquals(dtoUpdate.duracao(), atualizada.duracao());
        assertEquals(dtoUpdate.idioma(), atualizada.idioma());
        assertEquals(dtoUpdate.idAlbum(), atualizada.album().id());
        assertEquals(dtoUpdate.idGenero(), atualizada.genero().id());
        assertEquals(dtoUpdate.idComposicao(), atualizada.composicao().id());
    }

    @Test
    void apagarFaixa_deveRemoverComSucesso() {
        // composicao exclusiva desta faixa
        ComposicaoResponseDTO comp = criarComposicaoSimples(1L);

        FaixaDTO dto = new FaixaDTO(
            "Faixa Para Deletar",
            20,
            2.00,
            "Portuguese",
            1L,
            1L,
            1L,
            comp.id()
        );

        FaixaResponseDTO criada = faixaService.create(dto);

        RestAssured.given()
        .when()
            .delete("/faixas/" + criada.id())
        .then()
            .statusCode(204);

        // depois do delete, GET deve retornar 400
        RestAssured.given()
        .when()
            .get("/faixas/" + criada.id())
        .then()
            .statusCode(400);
    }

    // ---------- CONSULTAS ESPECÍFICAS ----------

    @Test
    void buscarPorTitulo_deveEncontrarFaixaBlackSwanPorTrecho() {
        RestAssured.given()
        .when()
            .get("/faixas/titulo/Black")
        .then()
            .statusCode(200)
            .body("titulo", hasItem("Black Swan"));
    }

    @Test
    void buscarPorAlbum_deveRetornarFaixasDoAlbum1() {
        RestAssured.given()
        .when()
            .get("/faixas/album/1")
        .then()
            .statusCode(200)
            .body("titulo", containsInAnyOrder("ON", "Black Swan"));
    }

    @Test
    void buscarPorArtistaParticipante_deveRetornarBlackSwanParaTaeyeon() {
        // Taeyeon = ProjetoMusical id = 3 (import-dev.sql)
        RestAssured.given()
        .when()
            .get("/faixas/participacao/3")
        .then()
            .statusCode(200)
            .body("titulo", hasItem("Black Swan"));
    }

    @Test
    void buscarPorCompositor_deveRetornarFaixasDeRM() {
        // RM = ProjetoMusical id = 1
        RestAssured.given()
        .when()
            .get("/faixas/compositor/1")
        .then()
            .statusCode(200)
            .body("titulo", hasItems("ON", "Black Swan"));
    }

    @Test
    void buscarPorGenero_deveRetornarFaixasKPop() {
        // Genero 1 = K-Pop, no import-dev.sql apenas "ON" está com idGenero=1
        RestAssured.given()
        .when()
            .get("/faixas/genero/1")
        .then()
            .statusCode(200)
            .body("titulo", hasItem("ON"));
    }

    @Test
    void buscarPorIdioma_deveRetornarFaixasEmCoreano() {
        RestAssured.given()
        .when()
            .get("/faixas/idioma/Korean")
        .then()
            .statusCode(200)
            .body("titulo", hasItems("ON", "Black Swan", "Alcohol-Free"));
    }

    @Test
    void buscarPorTipoVersao_deveRetornarFaixasOriginais() {
        // 1 = ORIGINAL no enum TipoVersao
        RestAssured.given()
        .when()
            .get("/faixas/find/tipoVersao/1")
        .then()
            .statusCode(200)
            .body("size()", greaterThanOrEqualTo(4),
                  "titulo", hasItems(
                        "ON",
                        "Black Swan",
                        "Alcohol-Free",
                        "Summer Love (feat Justin Bieber)"
                  ));
    }
}
