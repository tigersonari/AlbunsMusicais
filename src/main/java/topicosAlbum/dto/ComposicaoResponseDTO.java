package topicosAlbum.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import topicosAlbum.model.Artista;
import topicosAlbum.model.Composicao;
import topicosAlbum.model.GrupoMusical;

public record ComposicaoResponseDTO(
    Long id,
    LocalDate data,
    List<ProjetoMusicalResponseDTO> compositores
) {
    public static ComposicaoResponseDTO valueOf(Composicao c) {
        return new ComposicaoResponseDTO(
            c.getId(),
            c.getData(),
            c.getProjetoMusical().stream()
                .map(p -> {
                    if (p instanceof Artista a) return ArtistaResponseDTO.valueOf(a);
                    if (p instanceof GrupoMusical g) return GrupoMusicalResponseDTO.valueOf(g);
                    throw new IllegalArgumentException("Tipo desconhecido");
                })
                .collect(Collectors.toList())
        );
    }
}
