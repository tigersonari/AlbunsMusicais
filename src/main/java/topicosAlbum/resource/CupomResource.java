package topicosAlbum.resource;

import java.math.BigDecimal;
import java.math.RoundingMode;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import topicosAlbum.dto.CupomDTO;
import topicosAlbum.dto.CupomResponseDTO;
import topicosAlbum.exception.ValidationException;

@Path("/cupons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CupomResource {

    @POST
    @Path("/validar")
    @RolesAllowed({"ADM", "USER"})
    public Response validar(@Valid CupomDTO dto) {

        String codigo = dto.codigo().trim().toUpperCase();
        BigDecimal percentual;

        switch (codigo) {
            case "ALBUM10" -> percentual = new BigDecimal("10");
            case "KPOP15" -> percentual = new BigDecimal("15");
            case "FRETE20" -> percentual = new BigDecimal("20");
            default -> throw ValidationException.of("cupom", "Cupom inválido.");
        }

        BigDecimal valorDesconto = dto.total()
            .multiply(percentual)
            .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);

        BigDecimal totalComDesconto = dto.total().subtract(valorDesconto);

        return Response.ok(
            new CupomResponseDTO(
                codigo,
                percentual,
                valorDesconto,
                totalComDesconto,
                "Cupom aplicado com sucesso."
            )
        ).build();
    }
}