package topicosAlbum.dto;

import topicosAlbum.model.Genero;

public record GeneroResponseDTO(

    Long id,
    String nomeGenero,
    String descricao

) {
    public static GeneroResponseDTO valueOf(Genero genero) {
        return new GeneroResponseDTO(
            genero.getId(),
            genero.getNomeGenero(),
            genero.getDescricao()
        );
    }
}
