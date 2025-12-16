package topicosAlbum.service;

import java.util.Random;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PaymentGatewayFake {

    private final Random random = new Random();

    /** Retorna true = aprovado, false = rejeitado */
    public boolean processarPagamento(String metodo, Long idPagamento) {

        switch (metodo.toUpperCase()) {

            case "PIX":
                return true; // PIX sempre aprova automaticamente no seu sistema

            case "BOLETO":
                // boleto só aprova depois do scheduler rodar uma vez
                return random.nextBoolean();

            case "CARTAO":
                // 80% de chance de aprovação
                return random.nextInt(10) < 8;

            default:
                return false;
        }
    }
}
