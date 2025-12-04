package topicosAlbum;

import java.time.LocalDate;

import org.hamcrest.CoreMatchers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import jakarta.inject.Inject;
import topicosAlbum.dto.ArtistaDTO;
import topicosAlbum.dto.ArtistaResponseDTO;
import topicosAlbum.exception.ValidationException;
import topicosAlbum.service.ArtistaService;

@QuarkusTest
class ArtistaResourceTest {

    @Inject
    ArtistaService artistaService;

    /**
     * Helper que aplica o header Authorization usando a sua classe TokenUtils
     * (você disse que já criou). Usa token admin para testes que fazem escrita.
     */
    private RequestSpecification admin() {
        String token = TokenUtils.getAdminToken();
        return RestAssured.given().header("Authorization", "Bearer " + token);
    }

    // -------------------------------------------------------
    // CRUD BÁSICO
    // -------------------------------------------------------

    @Test
    void buscarTodosArtistas_deveRetornar200() {
        admin()
            .when()
                .get("/artistas")
            .then()
                .statusCode(200);
    }

    @Test
    void incluirArtista_deveRetornar201EBodyCorreto() {
        ArtistaDTO dto = new ArtistaDTO(
            "Artista Teste Completo",
            "Tester",
            LocalDate.of(2000, 1, 1),
            "Brasileira",
            "Produtor",
            4L // Universal Music no import-dev.sql
        );

        admin()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post("/artistas")
        .then()
            .statusCode(201)
            .body(
                "id",              CoreMatchers.notNullValue(),
                "nomeCompleto",    CoreMatchers.is("Artista Teste Completo"),
                "nomeArtistico",   CoreMatchers.is("Tester"),
                "nacionalidade",   CoreMatchers.is("Brasileira"),
                "funcaoPrincipal", CoreMatchers.is("Produtor"),
                "empresa.id",      CoreMatchers.is(4),
                "empresa.nomeEmpresa", CoreMatchers.is("Universal Music")
            );
    }

    @Test
    void alterarArtista_deveAtualizarDadosCorretamente() {
        // cria via service para não depender da seed
        ArtistaDTO dto = new ArtistaDTO(
            "Nome Original",
            "Orig",
            LocalDate.of(1995, 5, 5),
            "Argentina",
            "Rapper",
            1L
        );

        ArtistaResponseDTO criado = artistaService.create(dto);
        Long id = criado.id();

        ArtistaDTO dtoUpdate = new ArtistaDTO(
            "Nome Atualizado",
            "Atual",
            LocalDate.of(1996, 6, 6),
            "Chilena",
            "Vocalista",
            2L
        );

        admin()
            .contentType(ContentType.JSON)
            .body(dtoUpdate)
        .when()
            .put("/artistas/" + id)
        .then()
            .statusCode(204);

        ArtistaResponseDTO aposUpdate = artistaService.findById(id);

        assertEquals(dtoUpdate.nomeCompleto(),    aposUpdate.nomeCompleto());
        assertEquals(dtoUpdate.nomeArtistico(),   aposUpdate.nomeArtistico());
        assertEquals(dtoUpdate.dataNascimento(),  aposUpdate.dataNascimento());
        assertEquals(dtoUpdate.nacionalidade(),   aposUpdate.nacionalidade());
        assertEquals(dtoUpdate.funcaoPrincipal(), aposUpdate.funcaoPrincipal());
        assertEquals(dtoUpdate.idEmpresa(),       aposUpdate.empresa().id());
    }

    @Test
    void apagarArtista_deveRemoverComSucesso() {
        ArtistaDTO dto = new ArtistaDTO(
            "Artista Para Apagar",
            "DeleteMe",
            LocalDate.of(1990, 1, 1),
            "Portuguesa",
            "Produtor",
            3L
        );

        ArtistaResponseDTO criado = artistaService.create(dto);
        Long id = criado.id();

        admin()
        .when()
            .delete("/artistas/" + id)
        .then()
            .statusCode(204);

        // depois de apagar, buscar deve lançar ValidationException
        assertThrows(ValidationException.class, () -> artistaService.findById(id));
    }

    // -------------------------------------------------------
    // CONSULTAS USANDO import-dev.sql
    //
    // Seed (resumo):
    //  Artistas (id):
    //    1: RM (empresa 1)
    //    2: Suga (empresa 1)
    //    3: Taeyeon (empresa 2)
    //    5: Nayeon (empresa 3)
    //    6: Chaeyoung (empresa 3)
    //    7: Justin Bieber (empresa 4)
    // -------------------------------------------------------

    @Test
    void buscarPorNomeArtistico_deveRetornarRM() {
        admin()
        .when()
            .get("/artistas/nome-artistico/RM")
        .then()
            .statusCode(200)
            .body("[0].nomeArtistico", CoreMatchers.is("RM"),
                  "[0].nomeCompleto",  CoreMatchers.is("Kim Namjoon"));
    }

    @Test
    void buscarPorNomeCompleto_deveRetornarSuga() {
        admin()
        .when()
            .get("/artistas/nome/Min Yoongi")
        .then()
            .statusCode(200)
            .body("[0].nomeArtistico", CoreMatchers.is("Suga"),
                  "[0].nomeCompleto",  CoreMatchers.is("Min Yoongi"));
    }

    @Test
    void buscarPorNacionalidade_deveRetornarCoreanos() {
        admin()
        .when()
            .get("/artistas/nacionalidade/Coreana")
        .then()
            .statusCode(200)
            .body("nacionalidade", CoreMatchers.hasItem("Coreana"));
    }

    @Test
    void buscarPorFuncaoPrincipal_deveRetornarRappers() {
        admin()
        .when()
            .get("/artistas/funcao/Rapper")
        .then()
            .statusCode(200)
            .body("funcaoPrincipal", CoreMatchers.hasItem("Rapper"));
    }

    @Test
    void buscarPorEmpresa_deveRetornarArtistasDaHY() {
        // Empresa 1 = HY Entertainment
        admin()
        .when()
            .get("/artistas/empresa/1")
        .then()
            .statusCode(200)
            .body("nomeArtistico", CoreMatchers.hasItems("RM", "Suga"));
    }

    // -------------------------------------------------------
    // RELACIONAMENTOS
    //
    // Grupos:
    //   4: BTS (membros: 1=RM, 2=Suga)
    //   8: Twice (membros: 5=Nayeon, 6=Chaeyoung)
    //
    // Álbuns:
    //   1: MAP OF THE SOUL: 7 -> projetoMusical: 4 (BTS), 1 (RM), 2 (Suga)
    //   2: Taste of Love       -> projetoMusical: 8 (Twice), 5 (Nayeon), 6 (Chaeyoung)
    //
    // Participações:
    //   Taeyeon (id=3) participa na faixa 2 (Black Swan) -> álbum 1
    //   Justin Bieber (id=7) participa na faixa 4 -> álbum 2
    //
    // Composições:
    //   comp 1 e 2: RM e Suga
    //   comp 3: Nayeon e Chaeyoung
    //   comp 4: Nayeon, Chaeyoung, Justin Bieber
    // -------------------------------------------------------

    @Test
    void buscarGruposDeRM_deveRetornarBTS() {
        admin()
        .when()
            .get("/artistas/1/grupos")
        .then()
            .statusCode(200)
            .body("[0].nomeGrupo", CoreMatchers.is("BTS"));
    }

    @Test
    void buscarAlbunsPrincipaisDeRM_deveRetornarMapOfTheSoul7() {
        admin()
        .when()
            .get("/artistas/1/albuns")
        .then()
            .statusCode(200)
            .body("[0].titulo", CoreMatchers.is("MAP OF THE SOUL: 7"));
    }

    @Test
    void buscarAlbunsComParticipacaoDeTaeyeon_deveRetornarAlbum1() {
        admin()
        .when()
            .get("/artistas/3/albuns/participacoes")
        .then()
            .statusCode(200)
            .body("[0].titulo", CoreMatchers.is("MAP OF THE SOUL: 7"));
    }

    @Test
    void buscarFaixasParticipadasPorTaeyeon_deveRetornarBlackSwan() {
        var faixas = artistaService.findTodasFaixasRelacionadas(3L);
        // garante que veio pelo menos uma faixa
        assertFalse(faixas.isEmpty(), "Nenhuma faixa encontrada para Taeyeon (id=3)");

        // garante que Black Swan está na lista
        assertTrue(
            faixas.stream().anyMatch(f -> "Black Swan".equals(f.titulo())),
            "Deveria conter a faixa 'Black Swan' entre as participações da Taeyeon"
        );
    }

    @Test
    void buscarTodasFaixasRelacionadasARm_deveConterON() {
        admin()
        .when()
            .get("/artistas/1/faixas/todas")
        .then()
            .statusCode(200)
            .body("titulo", CoreMatchers.hasItem("ON"));
    }

    @Test
    void buscarComposicoesDeRM_deveConterData2019_08_01() {
        admin()
        .when()
            .get("/artistas/1/composicoes")
        .then()
            .statusCode(200)
            .body("data", CoreMatchers.hasItem("2019-08-01"));
    }

    // -------------------------------------------------------
    // EXTRA: valida integração service + resource rapidamente
    // -------------------------------------------------------

    @Test
    void criarViaServiceEBuscarViaEndpoint_deveManterConsistencia() {
        ArtistaDTO dto = new ArtistaDTO(
            "Consistencia Service/Resource",
            "Consist",
            LocalDate.of(1999, 9, 9),
            "Chilena",
            "Vocalista",
            4L
        );

        ArtistaResponseDTO criado = artistaService.create(dto);

        admin()
        .when()
            .get("/artistas/" + criado.id())
        .then()
            .statusCode(200)
            .body(
                "nomeArtistico",   CoreMatchers.is("Consist"),
                "funcaoPrincipal", CoreMatchers.is("Vocalista"),
                "empresa.id",      CoreMatchers.is(4)
            );
    }
}
