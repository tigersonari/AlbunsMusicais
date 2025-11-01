package topicosAlbum.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import topicosAlbum.model.GrupoMusical;

public record GrupoMusicalResponseDTO(
    Long id,
    String nomeGrupo,
    LocalDate dataInicio,
    LocalDate dataTermino,
    EmpresaResponseDTO empresa,
    List<ArtistaResponseDTO> membros
) implements ProjetoMusicalResponseDTO {  
    public static GrupoMusicalResponseDTO valueOf(GrupoMusical g) {
        return new GrupoMusicalResponseDTO(
            g.getId(),
            g.getNomeGrupo(),
            g.getDataInicio(),
            g.getDataTermino(),
            g.getEmpresa() != null ? EmpresaResponseDTO.valueOf(g.getEmpresa()) : null,
            g.getMembros() != null 
                ? g.getMembros().stream().map(ArtistaResponseDTO::valueOf).collect(Collectors.toList())
                : List.of()
        );
    }
}
