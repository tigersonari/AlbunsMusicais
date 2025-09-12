package topicosAlbum.dto;

import java.time.LocalDate;

public record ArtistaDTO(
    String nome,
    String nacionalidade,
    LocalDate dataNascimento,
    String instrumentoPrincipal, 
    String info 
) {
}
