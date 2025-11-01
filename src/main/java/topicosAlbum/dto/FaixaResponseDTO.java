package topicosAlbum.dto;

import java.util.List;
import java.util.stream.Collectors;
import topicosAlbum.model.Faixa;
import topicosAlbum.model.TipoVersao;

public record FaixaResponseDTO(
    Long id,
    String titulo,
    Integer numeroFaixa,
    Double duracao,
    String idioma,
    TipoVersao tipoVersao,

    AlbumResponseDTO album,

    GeneroResponseDTO genero,
    ComposicaoResponseDTO composicao,
    List<ParticipacaoResponseDTO> participacoes
) {
    public static FaixaResponseDTO valueOf(Faixa f) {
        return new FaixaResponseDTO(
            f.getId(),
            f.getTitulo(),
            f.getNumeroFaixa(),
            f.getDuracao(),
            f.getIdioma(),
            f.getTipoVersao(),

            f.getAlbum() != null ? AlbumResponseDTO.valueOf(f.getAlbum()) : null,

            f.getGenero() != null ? GeneroResponseDTO.valueOf(f.getGenero()) : null,
            f.getComposicao() != null ? ComposicaoResponseDTO.valueOf(f.getComposicao()) : null,

            f.getParticipacoes() != null
                ? f.getParticipacoes().stream()
                      .map(ParticipacaoResponseDTO::valueOf)
                      .collect(Collectors.toList())
                : List.of()
        );
    }
}
