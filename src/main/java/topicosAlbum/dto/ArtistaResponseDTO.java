package topicosAlbum.dto;

import java.time.LocalDate;

import topicosAlbum.model.Artista;

public record ArtistaResponseDTO(
    Long id,
    String nomeCompleto,
    String nomeArtistico,
    LocalDate dataNascimento,
    String nacionalidade,
    String funcaoPrincipal,
    EmpresaResponseDTO empresa
) implements ProjetoMusicalResponseDTO {   // ✅ polimorfismo

    public static ArtistaResponseDTO valueOf(Artista a) {
        return new ArtistaResponseDTO(
            a.getId(),
            a.getNomeCompleto(),
            a.getNomeArtistico(),
            a.getDataNascimento(),
            a.getNacionalidade(),
            a.getFuncaoPrincipal(),
            a.getEmpresa() != null ? EmpresaResponseDTO.valueOf(a.getEmpresa()) : null
        );
    }
}
