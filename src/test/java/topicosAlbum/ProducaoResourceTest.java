package topicosAlbum;


import java.time.LocalDate;

import org.hamcrest.CoreMatchers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import topicosAlbum.dto.EmpresaDTO;
import topicosAlbum.dto.EmpresaResponseDTO;
import topicosAlbum.dto.ProducaoDTO;
import topicosAlbum.dto.ProducaoResponseDTO;
import topicosAlbum.exception.ValidationException;
import topicosAlbum.service.EmpresaService;
import topicosAlbum.service.ProducaoService;

@QuarkusTest
class ProducaoResourceTest {

    @Inject
    ProducaoService producaoService;

    @Inject
    EmpresaService empresaService;

    // -------------------------------------------------------------------------
    // CRUD BÁSICO
    // -------------------------------------------------------------------------

    @Test
    void buscarTodasProducoes_deveRetornar200() {
        RestAssured.given()
            .when()
                .get("/producoes")
            .then()
                .statusCode(200);
    }

    @Test
    void incluirProducao_deveRetornar201EBodyCorreto() {
        // cria empresa exclusiva para este teste
        EmpresaDTO empresaDTO = new EmpresaDTO(
            "Empresa Teste Producao 1",
            "11111111000191",
            "Seoul",
            "contato1@empresa.com"
        );
        EmpresaResponseDTO empresa = empresaService.create(empresaDTO);

        ProducaoDTO dto = new ProducaoDTO(
            "Produtor Teste",
            "Eng Grav Teste",
            "Eng Mix Teste",
            "Eng Mast Teste",
            LocalDate.of(2024, 1, 10),
            empresa.id()
        );

        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post("/producoes")
        .then()
            .statusCode(201)
            .body("id", CoreMatchers.notNullValue(),
                  "produtor", CoreMatchers.is("Produtor Teste"),
                  "engenheiroGravacao", CoreMatchers.is("Eng Grav Teste"),
                  "engenheiroMixagem", CoreMatchers.is("Eng Mix Teste"),
                  "engenheiroMasterizacao", CoreMatchers.is("Eng Mast Teste"),
                  "empresa.id", CoreMatchers.is(empresa.id().intValue()));
    }

    @Test
    void alterarProducao_deveAtualizarDadosCorretamente() {
        // empresa exclusiva
        EmpresaDTO empresaDTO = new EmpresaDTO(
            "Empresa Teste Producao 2",
            "22222222000191",
            "Seoul",
            "contato2@empresa.com"
        );
        EmpresaResponseDTO empresa = empresaService.create(empresaDTO);

        // cria produção inicial via service
        ProducaoDTO dtoInicial = new ProducaoDTO(
            "Produtor Original",
            "Eng Grav Original",
            "Eng Mix Original",
            "Eng Mast Original",
            LocalDate.of(2023, 1, 1),
            empresa.id()
        );
        ProducaoResponseDTO criada = producaoService.create(dtoInicial);
        Long id = criada.id();

        // monta DTO de atualização
        ProducaoDTO dtoUpdate = new ProducaoDTO(
            "Produtor Atualizado",
            "Eng Grav Atualizado",
            "Eng Mix Atualizado",
            "Eng Mast Atualizado",
            LocalDate.of(2023, 6, 1),
            empresa.id() // mesma empresa
        );

        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(dtoUpdate)
        .when()
            .put("/producoes/" + id)
        .then()
            .statusCode(204);

        // valida via GET do próprio service
        ProducaoResponseDTO aposUpdate = producaoService.findById(id);

        assertEquals(dtoUpdate.produtor(),                aposUpdate.produtor());
        assertEquals(dtoUpdate.engenheiroGravacao(),     aposUpdate.engenheiroGravacao());
        assertEquals(dtoUpdate.engenheiroMixagem(),      aposUpdate.engenheiroMixagem());
        assertEquals(dtoUpdate.engenheiroMasterizacao(), aposUpdate.engenheiroMasterizacao());
        assertEquals(dtoUpdate.dataProducao(),           aposUpdate.dataProducao());
        assertEquals(dtoUpdate.idEmpresa(),              aposUpdate.empresa().id());
    }

    @Test
    void apagarProducao_deveRemoverComSucesso() {
        // empresa exclusiva
        EmpresaDTO empresaDTO = new EmpresaDTO(
            "Empresa Teste Producao 3",
            "33333333000191",
            "Seoul",
            "contato3@empresa.com"
        );
        EmpresaResponseDTO empresa = empresaService.create(empresaDTO);

        ProducaoDTO dto = new ProducaoDTO(
            "Produtor Para Apagar",
            "Eng Grav X",
            "Eng Mix X",
            "Eng Mast X",
            LocalDate.of(2022, 1, 1),
            empresa.id()
        );

        ProducaoResponseDTO criada = producaoService.create(dto);
        Long id = criada.id();

        RestAssured.given()
        .when()
            .delete("/producoes/" + id)
        .then()
            .statusCode(204);

        // depois de apagar, o service deve lançar ValidationException ao buscar
        assertThrows(ValidationException.class, () -> producaoService.findById(id));
    }

    // -------------------------------------------------------------------------
    // CONSULTAS USANDO OS DADOS DO import-dev.sql
    // (não mexemos nas produções de empresa 1 e 3)
    // -------------------------------------------------------------------------

    @Test
    void buscarPorProdutor_deveRetornarListaComNomeCorreto() {
        // import-dev.sql: produz "Suga" para empresa 1
        RestAssured.given()
        .when()
            .get("/producoes/find/produtor/Suga")
        .then()
            .statusCode(200)
            .body("[0].produtor", CoreMatchers.containsString("Suga"));
    }

    @Test
    void buscarPorEngenheiroMixagem_deveRetornarProducoesRelacionadas() {
        // import-dev.sql: "El Capitxn" na produção 1
        RestAssured.given()
        .when()
            .get("/producoes/find/mixagem/El Capitxn")
        .then()
            .statusCode(200)
            .body("[0].engenheiroMixagem", CoreMatchers.containsString("El Capitxn"));
    }

    @Test
    void buscarPorDataProducao_deveRetornarProducoesNaData() {
        // import-dev.sql: dataproducao = 2019-11-01 para produção do Suga
        RestAssured.given()
        .when()
            .get("/producoes/find/data/2019-11-01")
        .then()
            .statusCode(200)
            .body("[0].produtor", CoreMatchers.is("Suga"));
    }

    @Test
    void buscarPorPeriodoProducao_deveRetornarProducoesNoIntervalo() {
        // período que inclui 2019-11-01
        RestAssured.given()
        .when()
            .get("/producoes/find/periodo?inicio=2019-01-01&fim=2019-12-31")
        .then()
            .statusCode(200)
            .body("[0].produtor", CoreMatchers.is("Suga"));
    }

    @Test
    void buscarPorEmpresa_deveRetornarProducoesDaEmpresa() {
        // empresa 1 (HY) produz "MAP OF THE SOUL: 7" (Suga)
        RestAssured.given()
        .when()
            .get("/producoes/find/empresa/1")
        .then()
            .statusCode(200)
            .body("[0].empresa.id", CoreMatchers.is(1));
    }

    @Test
    void buscarPorAlbum_deveRetornarProducaoCorreta() {
        // álbum 1 (MAP OF THE SOUL: 7) -> producao 1 (Suga)
        RestAssured.given()
        .when()
            .get("/producoes/find/album/1")
        .then()
            .statusCode(200)
            .body("produtor", CoreMatchers.is("Suga"));
    }
}
