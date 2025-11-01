package topicosAlbum.dto;

import java.time.LocalDate;

import topicosAlbum.model.Producao;

public record ProducaoResponseDTO(
    Long id,
    String produtor,
    String engenheiroGravacao,
    String engenheiroMixagem,
    String engenheiroMasterizacao,
    LocalDate dataProducao,
    EmpresaResponseDTO empresa
) {
    public static ProducaoResponseDTO valueOf(Producao producao) {
        return new ProducaoResponseDTO(
            producao.getId(),
            producao.getProdutor(),
            producao.getEngenheiroGravacao(),
            producao.getEngenheiroMixagem(),
            producao.getEngenheiroMasterizacao(),
            producao.getDataProducao(),
            producao.getEmpresa() != null
                ? EmpresaResponseDTO.valueOf(producao.getEmpresa())
                : null
        );
    }
}
