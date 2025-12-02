package topicosAlbum;

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
import topicosAlbum.exception.ValidationException;
import topicosAlbum.service.EmpresaService;

@QuarkusTest
class EmpresaResourceTest {

    @Inject
    EmpresaService empresaService;

    @Test
    void buscarTodasEmpresas_deveRetornar200() {
        RestAssured.given()
            .when()
                .get("/empresas")
            .then()
                .statusCode(200);
    }

    @Test
    void incluirEmpresa_deveRetornar201EBodyCorreto() {
        // CNPJ diferente dos do import-dev.sql
        EmpresaDTO dto = new EmpresaDTO(
            "Empresa Teste Unidade",
            "99999999000199",
            "Teste City",
            "contato@empresa-teste.com"
        );

        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post("/empresas")
        .then()
            .statusCode(201)
            .body("id", CoreMatchers.notNullValue(),
                  "nomeEmpresa", CoreMatchers.is("Empresa Teste Unidade"),
                  "cnpj", CoreMatchers.is("99999999000199"),
                  "localizacao", CoreMatchers.is("Teste City"),
                  "contato", CoreMatchers.is("contato@empresa-teste.com"));
    }

    @Test
    void alterarEmpresa_deveAtualizarDadosCorretamente() {
        // 1) Cria empresa via service (mais direto)
        EmpresaDTO dto = new EmpresaDTO(
            "Empresa Atualizar",
            "99999999000188",
            "Cidade A",
            "contato@atualizar.com"
        );

        EmpresaResponseDTO criada = empresaService.create(dto);
        Long id = criada.id();

        // 2) DTO de atualização
        EmpresaDTO dtoUpdate = new EmpresaDTO(
            "Empresa Atualizada",
            "99999999000187",
            "Cidade B",
            "contato@atualizada.com"
        );

        // 3) Chama o PUT pelo endpoint REST
        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(dtoUpdate)
        .when()
            .put("/empresas/" + id)
        .then()
            .statusCode(204);

        // 4) Busca pelo service e valida alteração
        EmpresaResponseDTO aposUpdate = empresaService.findById(id);

        assertEquals(dtoUpdate.nomeEmpresa(), aposUpdate.nomeEmpresa());
        assertEquals(dtoUpdate.cnpj(),        aposUpdate.cnpj());
        assertEquals(dtoUpdate.localizacao(), aposUpdate.localizacao());
        assertEquals(dtoUpdate.contato(),     aposUpdate.contato());
    }

    @Test
    void apagarEmpresa_deveRemoverComSucesso() {
        // 1) Cria empresa via service
        EmpresaDTO dto = new EmpresaDTO(
            "Empresa Apagar",
            "99999999000177",
            "Cidade X",
            "contato@apagar.com"
        );

        EmpresaResponseDTO criada = empresaService.create(dto);
        Long id = criada.id();

        // 2) Deleta via endpoint REST
        RestAssured.given()
        .when()
            .delete("/empresas/" + id)
        .then()
            .statusCode(204);

        // 3) Verifica que não existe mais via service
        assertThrows(ValidationException.class, () -> empresaService.findById(id));
    }

    @Test
    void buscarPorNome_deveRetornarListaComFiltro() {
        // Usa dados do import-dev.sql (por exemplo, "HY Entertainment")
        RestAssured.given()
        .when()
            .get("/empresas/find/nome/HY")
        .then()
            .statusCode(200)
            .body("[0].nomeEmpresa", CoreMatchers.containsString("HY"));
    }

    @Test
    void buscarPorCnpj_deveRetornarEmpresaCorreta() {
        // Usa um CNPJ conhecido do import-dev.sql
        RestAssured.given()
        .when()
            .get("/empresas/find/cnpj/12345678000199")
        .then()
            .statusCode(200)
            .body("nomeEmpresa", CoreMatchers.is("HY Entertainment"),
                  "cnpj", CoreMatchers.is("12345678000199"));
    }
}
