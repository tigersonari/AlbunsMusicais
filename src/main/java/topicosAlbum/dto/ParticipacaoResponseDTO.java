package topicosAlbum.dto;

import java.util.List;
import java.util.stream.Collectors;

import topicosAlbum.model.Artista;
import topicosAlbum.model.GrupoMusical;
import topicosAlbum.model.Participacao;

public record ParticipacaoResponseDTO(
    Long id,
    String papel,
    Boolean destaque,
    List<ProjetoMusicalResponseDTO> participantes
) {

    public static ParticipacaoResponseDTO valueOf(Participacao p) {
        return new ParticipacaoResponseDTO(
            p.getId(),
            p.getPapel(),
            p.isDestaque(),
            p.getProjetoMusical().stream()
                .map(pm -> {
                    if (pm instanceof Artista a) return ArtistaResponseDTO.valueOf(a);
                    if (pm instanceof GrupoMusical g) return GrupoMusicalResponseDTO.valueOf(g);
                    throw new IllegalArgumentException("Tipo de projeto musical desconhecido");
                })
                .collect(Collectors.toList())
        );
    }
}
