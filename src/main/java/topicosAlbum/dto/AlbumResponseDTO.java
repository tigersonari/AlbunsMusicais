package topicosAlbum.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import topicosAlbum.model.Album;
import topicosAlbum.model.Artista;
import topicosAlbum.model.Formato;
import topicosAlbum.model.GrupoMusical;
import topicosAlbum.model.ProjetoMusical;

public record AlbumResponseDTO(
    Long id,
    String titulo,
    LocalDate lancamento,
    String descricao,
    Formato formato,
    ProducaoResponseDTO producao,
    List<ProjetoMusicalResponseDTO> artistasPrincipais,
    List<GeneroResponseDTO> generos
) {

    public static AlbumResponseDTO valueOf(Album album) {
        return new AlbumResponseDTO(
            album.getId(),
            album.getTitulo(),
            album.getLancamento(),
            album.getDescricao(),
            album.getFormato(),
            album.getProducao() != null ? ProducaoResponseDTO.valueOf(album.getProducao()) : null,
            album.getProjetoMusical() != null
                ? album.getProjetoMusical().stream()
                        .map(AlbumResponseDTO::toResponse)
                        .collect(Collectors.toList())
                : List.of(),
            album.getGeneros() != null
                ? album.getGeneros()
                        .stream()
                        .map(GeneroResponseDTO::valueOf)
                        .collect(Collectors.toList())
                : List.of()
        );
    }

    private static ProjetoMusicalResponseDTO toResponse(ProjetoMusical p) {
        if (p instanceof Artista a)
            return ArtistaResponseDTO.valueOf(a);
        if (p instanceof GrupoMusical g)
            return GrupoMusicalResponseDTO.valueOf(g);

        throw new IllegalArgumentException("Tipo desconhecido de ProjetoMusical: " + p.getClass().getName());
    }
}
