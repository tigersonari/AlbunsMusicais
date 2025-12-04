package topicosAlbum;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import jakarta.inject.Inject;
import topicosAlbum.dto.ArtistaResponseDTO;
import topicosAlbum.dto.ComposicaoDTO;
import topicosAlbum.dto.ComposicaoResponseDTO;
import topicosAlbum.dto.GrupoMusicalResponseDTO;
import topicosAlbum.service.ComposicaoService;

@QuarkusTest
public class ComposicaoResourceTest {

    @Inject
    ComposicaoService composicaoService;

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
            .get("/composicoes")
        .then()
            .statusCode(200)
            .body("size()", greaterThanOrEqualTo(4)); // 4 no import-dev.sql
    }

    @Test
    void buscarPorId_deveRetornarComposicaoDeOn() {
        // composicao 1 no import: data 2019-08-01, compositores 1 (RM) e 2 (Suga)
        admin()
        .when()
            .get("/composicoes/{id}", 1L)
        .then()
            .statusCode(200)
            .body("id", is(1),
                  "data", is("2019-08-01"),
                  "compositores.size()", is(2),
                  "compositores.id", hasItems(1, 2));
    }

    @Test
    void incluirComposicao_deveCriarNovaComCompositores() {
        ComposicaoDTO dto = new ComposicaoDTO(
            LocalDate.of(2022, 1, 1),
            List.of(1L, 4L) // RM (artista) + BTS (grupo)
        );

        admin()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post("/composicoes")
        .then()
            .statusCode(201)
            .body("id", notNullValue(),
                  "data", is("2022-01-01"),
                  "compositores.id", hasItems(1, 4));
    }

    @Test
    void alterarComposicao_deveAtualizarDataECompositores() {
        // cria uma composição só para este teste
        ComposicaoDTO dto = new ComposicaoDTO(
            LocalDate.of(2020, 1, 1),
            List.of(1L, 2L) // RM e Suga
        );

        ComposicaoResponseDTO criada = composicaoService.create(dto);

        ComposicaoDTO dtoUpdate = new ComposicaoDTO(
            LocalDate.of(2021, 5, 5),
            List.of(2L, 3L) // Suga e Taeyeon
        );

        admin()
            .contentType(ContentType.JSON)
            .body(dtoUpdate)
        .when()
            .put("/composicoes/{id}", criada.id())
        .then()
            .statusCode(204);

        // valida no service
        ComposicaoResponseDTO atualizada = composicaoService.findById(criada.id());
        Assertions.assertEquals(dtoUpdate.data(), atualizada.data());

        // extrai os IDs dos compositores (ArtistaResponseDTO ou GrupoMusicalResponseDTO)
        List<Long> idsCompositores = atualizada.compositores().stream()
            .map(pm -> {
                if (pm instanceof ArtistaResponseDTO a) {
                    return a.id();
                }
                if (pm instanceof GrupoMusicalResponseDTO g) {
                    return g.id();
                }
                throw new IllegalArgumentException("Tipo de ProjetoMusicalResponseDTO desconhecido: " + pm.getClass());
            })
            .toList();

        Assertions.assertTrue(idsCompositores.contains(2L));
        Assertions.assertTrue(idsCompositores.contains(3L));
        Assertions.assertEquals(2, idsCompositores.size());
    }

    @Test
    void apagarComposicao_deveRemoverComSucesso() {
        // cria composição que não está ligada a nenhuma faixa
        ComposicaoDTO dto = new ComposicaoDTO(
            LocalDate.of(2023, 3, 3),
            List.of(1L) // qualquer projeto válido
        );

        ComposicaoResponseDTO criada = composicaoService.create(dto);

        admin()
        .when()
            .delete("/composicoes/{id}", criada.id())
        .then()
            .statusCode(204);

        // após apagar, buscar por id deve retornar 400 (ValidationException)
        admin()
        .when()
            .get("/composicoes/{id}", criada.id())
        .then()
            .statusCode(400);
    }

    // ========== CONSULTAS ESPECÍFICAS ==========

    @Test
    void buscarPorData_deveRetornarComposicoesNaData() {
        // import: composicao 1 => 2019-08-01
        admin()
        .when()
            .get("/composicoes/data/{data}", "2019-08-01")
        .then()
            .statusCode(200)
            .body("size()", is(1),
                  "[0].id", is(1),
                  "[0].data", is("2019-08-01"));
    }

    @Test
    void buscarPorProjetoMusical_deveRetornarComposicoesDoProjeto() {
        // import: projeto 1 (RM) participa das composicoes 1 e 2
        admin()
        .when()
            .get("/composicoes/projeto/{idProjeto}", 1L)
        .then()
            .statusCode(200)
            .body("data", hasItems("2019-08-01", "2019-09-01"));
    }

    @Test
    void buscarPorFaixa_deveRetornarComposicaoDaFaixa() {
        // import: faixa 2 = 'Black Swan' -> composicao 2 (2019-09-01)
        admin()
        .when()
            .get("/composicoes/faixa/{idFaixa}", 2L)
        .then()
            .statusCode(200)
            .body("id", is(2),
                  "data", is("2019-09-01"));
    }
}
