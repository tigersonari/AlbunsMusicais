package topicosAlbum.dto;

import topicosAlbum.model.Album;
import topicosAlbum.model.AvaliacaoProfissional;

public record AvaliacaoProfissionalResponseDTO(
    Long id,
    String avaliador,
    String comentario,
    double nota,
    AlbumResponseDTO album
) {
    public static AvaliacaoProfissionalResponseDTO valueOf(AvaliacaoProfissional a, Album album) {
        return new AvaliacaoProfissionalResponseDTO(
            a.getId(),
            a.getAvaliador(),
            a.getComentario(),
            a.getNota(),
            album != null ? AlbumResponseDTO.valueOf(album) : null
        );
    }
}
