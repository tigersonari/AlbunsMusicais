package topicosAlbum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
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
class PedidoResourceTest {

    @Inject
    PedidoRepository pedidoRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    PagamentoRepository pagamentoRepository;

    private RequestSpecification admin() {
        String token = TokenUtils.getAdminToken();
        return given().header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }

    private RequestSpecification user() {
        String token = TokenUtils.getUserToken();
        return given().header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }

    @Transactional
    Long criarPedidoBasicoParaUser2() {
        Usuario usuario = usuarioRepository.findById(2L); // 'user'

        Pedido p = new Pedido();
        p.setUsuario(usuario);
        p.setStatus("CRIADO");
        p.setTotal(new BigDecimal("199.90"));
        p.setDataCriacao(LocalDateTime.now());
        p.setObservacao("Pedido de teste");

        pedidoRepository.persist(p);

        // cria pagamento padrão para evitar NPE no service
        Pagamento pag = new Pagamento();
        pag.setPedido(p);
        pag.setMetodoPagamento("PIX");
        pag.setStatus("PENDENTE");
        pag.setValor(new BigDecimal("199.90"));
        pag.setCodigoPagamento("COD-TESTE");

        pagamentoRepository.persist(pag);

        // não é obrigatório setar no Pedido, mas ajuda na navegação em memória
        p.setPagamento(pag);

        return p.getId();
    }

    @Test
    @DisplayName("GET /pedidos/{id} - deve retornar pedido existente")
    void buscarPorId_deveRetornarPedido() {
        Long idPedido = criarPedidoBasicoParaUser2();

        user()
            .when()
                .get("/pedidos/" + idPedido)
            .then()
                .statusCode(200)
                .body("id", is(idPedido.intValue()),
                      "total", is(199.90f));
    }

    @Test
    @DisplayName("GET /pedidos/usuario/{idUsuario} - deve retornar histórico do usuário")
    void buscarPorUsuario_deveRetornarLista() {
        Long idPedido = criarPedidoBasicoParaUser2();

        user()
            .when()
                .get("/pedidos/usuario/2")
            .then()
                .statusCode(200)
                .body("id", hasItem(idPedido.intValue()));
    }

    @Test
    @DisplayName("PUT /pedidos/{id}/cancelar - deve alterar status para CANCELADO")
    void cancelarPedido_deveAtualizarStatus() {
        Long idPedido = criarPedidoBasicoParaUser2();

        user()
            .when()
                .put("/pedidos/" + idPedido + "/cancelar")
            .then()
                .statusCode(204);

        user()
            .when()
                .get("/pedidos/" + idPedido)
            .then()
                .statusCode(200)
                .body("status", is("CANCELADO"));
    }
}
