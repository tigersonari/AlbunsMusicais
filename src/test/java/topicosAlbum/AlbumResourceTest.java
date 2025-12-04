package topicosAlbum;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import jakarta.inject.Inject;
import topicosAlbum.dto.AlbumDTO;
import topicosAlbum.dto.AlbumResponseDTO;
import topicosAlbum.dto.EmpresaDTO;
import topicosAlbum.dto.EmpresaResponseDTO;
import topicosAlbum.dto.ProducaoDTO;
import topicosAlbum.dto.ProducaoResponseDTO;
import topicosAlbum.exception.ValidationException;
import topicosAlbum.model.Formato;
import topicosAlbum.service.AlbumService;
import topicosAlbum.service.EmpresaService;
import topicosAlbum.service.ProducaoService;

@QuarkusTest
class AlbumResourceTest {

    @Inject
    AlbumService albumService;

    @Inject
    ProducaoService producaoService;

    @Inject
    EmpresaService empresaService;

    // Usado pra gerar CNPJs únicos em tempo de teste
    private static final AtomicLong CNPJ_SEQ = new AtomicLong(90000000000000L);

    // --------------- HELPERS ---------------

    private EmpresaResponseDTO criarEmpresaFake() {
        long cnpjNum = CNPJ_SEQ.incrementAndGet();
        String cnpj = String.valueOf(cnpjNum).substring(0, 14); // garante 14 dígitos

        EmpresaDTO dto = new EmpresaDTO(
            "Empresa Teste " + cnpj,
            cnpj,
            "Cidade Teste",
            "contato" + cnpj + "@teste.com"
        );

        return empresaService.create(dto);
    }

    private ProducaoResponseDTO criarProducaoParaEmpresa(Long idEmpresa) {
        ProducaoDTO dto = new ProducaoDTO(
            "Produtor Teste",
            "Eng Gravação Teste",
            "Eng Mixagem Teste",
            "Eng Masterização Teste",
            LocalDate.parse("2022-01-01"),
            idEmpresa
        );
        return producaoService.create(dto);
    }

    /**
     * Cria um álbum novo, com:
     * - Empresa nova
     * - Producao nova (1:1 com essa empresa)
     * - Album novo (1:1 com essa produção)
     */
    private AlbumResponseDTO criarAlbumParaTeste() {
        EmpresaResponseDTO empresa = criarEmpresaFake();
        ProducaoResponseDTO producao = criarProducaoParaEmpresa(empresa.id());

        AlbumDTO dto = new AlbumDTO(
            "Album Inicial Teste",
            LocalDate.parse("2022-02-02"),
            "Descrição inicial",
            Formato.LONGPLAY.ID,         // teu enum
            producao.id(),
            List.of(4L),                 // BTS
            List.of(1L)                  // K-Pop
        );

        return albumService.create(dto);
    }

    /**
     * Helper para obter um RequestSpecification com o header Authorization já aplicado.
     * Usa a sua classe TokenUtils (que você disse que já criou na pasta de testes).
     */
    private RequestSpecification admin() {
        String token = TokenUtils.getAdminToken();
        return given().header("Authorization", "Bearer " + token);
    }

    // --------------- CRUD BÁSICO ---------------

    @Test
    @DisplayName("GET /albums - deve retornar 200")
    void buscarTodosAlbuns_deveRetornar200() {
        admin()
            .when()
                .get("/albums")
            .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("POST /albums - deve criar novo álbum com sucesso")
    void incluirAlbum_deveRetornar201EBodyCorreto() {
        EmpresaResponseDTO empresa = criarEmpresaFake();
        ProducaoResponseDTO producao = criarProducaoParaEmpresa(empresa.id());

        AlbumDTO dto = new AlbumDTO(
            "Album Teste Novo",
            LocalDate.parse("2023-03-03"),
            "Descrição de teste",
            Formato.EP.ID,               // usa o ID do enum
            producao.id(),
            List.of(8L),                 // Twice
            List.of(1L, 3L)              // K-Pop, Pop
        );

        admin()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post("/albums")
        .then()
            .statusCode(201)
            .body("id", notNullValue(),
                  "titulo", is("Album Teste Novo"),
                  "descricao", is("Descrição de teste"),
                  "formato.id", is(dto.idFormato().intValue()),
                  "formato.nome", is("Ep"),
                  "producao.id", is(producao.id().intValue()));
    }

    @Test
    @DisplayName("PUT /albums/{id} - deve atualizar dados corretamente")
    void alterarAlbum_deveAtualizarDadosCorretamente() {
        AlbumResponseDTO criado = criarAlbumParaTeste();

        AlbumDTO dtoUpdate = new AlbumDTO(
            "Album Atualizado",
            LocalDate.parse("2024-04-04"),
            "Descrição atualizada",
            Formato.DELUXE.ID,
            criado.producao().id(),         // mesma produção
            List.of(4L, 1L),                // BTS + RM
            List.of(1L, 2L)                 // K-Pop + Hip-Hop
        );

        admin()
            .contentType(ContentType.JSON)
            .body(dtoUpdate)
        .when()
            .put("/albums/" + criado.id())
        .then()
            .statusCode(204);

        AlbumResponseDTO atualizado = albumService.findById(criado.id());

        assertEquals(dtoUpdate.titulo(), atualizado.titulo());
        assertEquals(dtoUpdate.descricao(), atualizado.descricao());
        assertEquals(dtoUpdate.idFormato(), atualizado.formato().ID);
    }

    @Test
    @DisplayName("DELETE /albums/{id} - deve remover com sucesso")
    void apagarAlbum_deveRemoverComSucesso() {
        AlbumResponseDTO criado = criarAlbumParaTeste();

        admin()
            .when()
                .delete("/albums/" + criado.id())
            .then()
                .statusCode(204);

        // service deve lançar ValidationException se não encontrar
        assertThrows(ValidationException.class,
            () -> albumService.findById(criado.id()));
    }

    // --------------- CONSULTAS (usando o import-dev.sql) ---------------

    @Test
    @DisplayName("GET /albums/{id} - deve retornar álbum existente (id=1)")
    void buscarPorId_deveRetornarAlbumExistente() {
        // import-dev.sql: album id=1 → "MAP OF THE SOUL: 7", formato LONGPLAY (5)
        admin()
            .when()
                .get("/albums/1")
            .then()
                .statusCode(200)
                .body("id", is(1),
                      "titulo", is("MAP OF THE SOUL: 7"),
                      "formato.id", is(Formato.LONGPLAY.ID.intValue()));
    }

    @Test
    @DisplayName("GET /albums/find/titulo/{titulo} - deve retornar álbuns por título")
    void buscarPorTitulo_deveRetornarAlbunsPorTitulo() {
        admin()
            .when()
                .get("/albums/find/titulo/MAP OF THE SOUL")
            .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(1),
                      "[0].titulo", containsString("MAP OF THE SOUL"));
    }

    @Test
    @DisplayName("GET /albums/find/ano/{ano} - deve retornar álbuns do ano de lançamento")
    void buscarPorAnoLancamento_deveRetornarAlbunsDoAno() {
        // import-dev: 2020 → MAP OF THE SOUL: 7, 2021 → Taste of Love
        admin()
            .when()
                .get("/albums/find/ano/2020")
            .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(1),
                      "[0].titulo", containsString("MAP OF THE SOUL"));

        admin()
            .when()
                .get("/albums/find/ano/2021")
            .then()
                .statusCode(200)
                .body("titulo", hasItem("Taste of Love"));
    }

    @Test
    @DisplayName("GET /albums/find/formato/{idFormato} - deve retornar álbuns por formato (LONGPLAY)")
    void buscarPorFormato_deveRetornarAlbunsPorFormato() {
        admin()
            .when()
                .get("/albums/find/formato/" + Formato.LONGPLAY.ID)
            .then()
                .statusCode(200)
                .body("titulo", hasItems("MAP OF THE SOUL: 7", "Taste of Love"));
    }

    @Test
    @DisplayName("GET /albums/find/projeto/{idProjetoMusical} - deve retornar álbuns do projeto principal (BTS)")
    void buscarPorArtistaPrincipal_deveRetornarAlbunsDoProjeto() {
        // projeto 4 = BTS, principal em MAP OF THE SOUL: 7
        admin()
            .when()
                .get("/albums/find/projeto/4")
            .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(1),
                      "[0].titulo", is("MAP OF THE SOUL: 7"));
    }

    @Test
    @DisplayName("GET /albums/find/participacao/{idProjetoMusical} - deve retornar álbuns com participação do projeto")
    void buscarPorParticipacao_deveRetornarAlbunsComParticipacao() {
        // projeto 7 = Justin Bieber, participa na faixa 4 do álbum 2
        admin()
            .when()
                .get("/albums/find/participacao/7")
            .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(1),
                      "titulo", hasItem("Taste of Love"));
    }

    @Test
    @DisplayName("GET /albums/find/colaboracao - deve responder 200 mesmo sem colaborações cadastradas")
    void buscarColaboracoesEntre_deveRetornar200MesmoSemResultados() {
        admin()
            .when()
                .get("/albums/find/colaboracao?idArtista1=5&idArtista2=7")
            .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("GET /albums/find/producao/empresa/{idEmpresa} - deve retornar álbuns produzidos pela empresa")
    void buscarPorEmpresaProducao_deveRetornarAlbunsDaEmpresa() {
        // empresa 1 produz álbum 1, empresa 3 produz álbum 2
        admin()
            .when()
                .get("/albums/find/producao/empresa/1")
            .then()
                .statusCode(200)
                .body("titulo", hasItem("MAP OF THE SOUL: 7"));

        admin()
            .when()
                .get("/albums/find/producao/empresa/3")
            .then()
                .statusCode(200)
                .body("titulo", hasItem("Taste of Love"));
    }

    @Test
    @DisplayName("GET /albums/find/produtor/{nome} - deve retornar álbuns de determinado produtor")
    void buscarPorProdutor_deveRetornarAlbunsDoProdutor() {
        admin()
            .when()
                .get("/albums/find/produtor/Suga")
            .then()
                .statusCode(200)
                .body("titulo", hasItem("MAP OF THE SOUL: 7"));
    }

    @Test
    @DisplayName("GET /albums/find/mixagem/{nome} - deve retornar álbuns por engenheiro de mixagem")
    void buscarPorEngMixagem_deveRetornarAlbunsPorEngMix() {
        admin()
            .when()
                .get("/albums/find/mixagem/El Capitxn")
            .then()
                .statusCode(200)
                .body("titulo", hasItem("MAP OF THE SOUL: 7"));
    }

    @Test
    @DisplayName("GET /albums/find/masterizacao/{nome} - deve retornar álbuns por engenheiro de masterização")
    void buscarPorEngMasterizacao_deveRetornarAlbunsPorEngMaster() {
        admin()
            .when()
                .get("/albums/find/masterizacao/JYP Studio")
            .then()
                .statusCode(200)
                .body("titulo", hasItem("Taste of Love"));
    }

    @Test
    @DisplayName("GET /albums/find/genero/{idGenero} - deve retornar álbuns por gênero")
    void buscarPorGenero_deveRetornarAlbunsPorGenero() {
        // gênero 1 = K-Pop, ligado aos dois álbuns
        admin()
            .when()
                .get("/albums/find/genero/1")
            .then()
                .statusCode(200)
                .body("titulo", hasItems("MAP OF THE SOUL: 7", "Taste of Love"));
    }

    @Test
    @DisplayName("GET /albums/find/faixa/{tituloFaixa} - deve retornar álbum que contém a faixa")
    void buscarPorTituloFaixa_deveRetornarAlbunsQueContemFaixa() {
        // faixa "Black Swan" pertence ao álbum 1
        admin()
            .when()
                .get("/albums/find/faixa/Black Swan")
            .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(1),
                      "[0].titulo", is("MAP OF THE SOUL: 7"));
    }
}
