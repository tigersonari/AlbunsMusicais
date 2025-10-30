package topicosAlbum.dto;

import java.time.LocalDate;

public record AlbumDTO(
    String titulo,
    LocalDate lancamento,
    String descricao,
    String formato, // será convertido para Enum Formato
    Long idProducao,
    Long idProjetoMusical // apenas 1 por enquanto — depois você pode trocar p/ lista
) {}
