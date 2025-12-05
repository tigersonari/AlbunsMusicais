package topicosAlbum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import io.restassured.specification.RequestSpecification;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.HttpHeaders;
import topicosAlbum.model.Pagamento;
import topicosAlbum.model.Pedido;
import topicosAlbum.model.Usuario;
import topicosAlbum.repository.PagamentoRepository;
import topicosAlbum.repository.PedidoRepository;
import topicosAlbum.repository.UsuarioRepository;

@QuarkusTest
class PagamentoResourceTest {

    @Inject
    PedidoRepository pedidoRepository;

    @Inject
    PagamentoRepository pagamentoRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    private RequestSpecification admin() {
        String token = TokenUtils.getAdminToken();
        return given().header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }

    private RequestSpecification user() {
        String token = TokenUtils.getUserToken();
        return given().header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }

    @Transactional
    Long criarPedidoComPagamentoPIX() {
        Usuario usuario = usuarioRepository.findById(2L);

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setStatus("CRIADO");
        pedido.setTotal(new BigDecimal("150.00"));
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setObservacao("Pedido para teste de pagamento");
        pedidoRepository.persist(pedido);

        Pagamento pagamento = new Pagamento();
        pagamento.setPedido(pedido);
        pagamento.setMetodoPagamento("PIX");
        pagamento.setStatus("PENDENTE");
        pagamento.setValor(new BigDecimal("150.00"));
        pagamento.setCodigoPagamento(null);

        pagamentoRepository.persist(pagamento);

        return pedido.getId();
    }

    @Test
    @DisplayName("POST /pagamentos/pix/{idPedido} - deve gerar PIX para pedido")
    void gerarPix_deveRetornar201EPreencherCodigoPagamento() {
        Long idPedido = criarPedidoComPagamentoPIX();

        user()
            .contentType(JSON)
        .when()
            .post("/pagamentos/pix/" + idPedido)
        .then()
            .statusCode(201)
            .body("id", notNullValue(),
                  "metodoPagamento", is("PIX"),
                  "status", is("PENDENTE"),
                  "codigoPagamento", notNullValue());
    }

    @Test
    @DisplayName("GET /pagamentos/{id} - deve buscar pagamento existente")
    void buscarPorId_deveRetornarPagamento() {
        Long idPedido = criarPedidoComPagamentoPIX();

        Number idNum =
            user()
                .contentType(JSON)
            .when()
                .post("/pagamentos/pix/" + idPedido)
            .then()
                .statusCode(201)
                .extract()
                .path("id");

        Long idPagamento = idNum.longValue();

        user()
            .when()
                .get("/pagamentos/" + idPagamento)
            .then()
                .statusCode(200)
                .body("id", is(idPagamento.intValue()),
                      "metodoPagamento", is("PIX"),
                      "status", is("PENDENTE"));
    }

    @Test
    @DisplayName("PUT /pagamentos/{id}/confirmar - deve aprovar pagamento e marcar pedido como PAGO")
    void confirmarPagamento_deveAtualizarStatus() {
        Long idPedido = criarPedidoComPagamentoPIX();

        Number idNum =
            user()
                .contentType(JSON)
            .when()
                .post("/pagamentos/pix/" + idPedido)
            .then()
                .statusCode(201)
                .extract()
                .path("id");

        Long idPagamento = idNum.longValue();

        user()
            .when()
                .put("/pagamentos/" + idPagamento + "/confirmar")
            .then()
                .statusCode(204);

        user()
            .when()
                .get("/pagamentos/" + idPagamento)
            .then()
                .statusCode(200)
                .body("status", is("APROVADO"));

        admin()
            .when()
                .get("/pedidos/" + idPedido)
            .then()
                .statusCode(200)
                .body("status", is("PAGO"));
    }
}
