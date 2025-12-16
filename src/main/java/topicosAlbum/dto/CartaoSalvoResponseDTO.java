package topicosAlbum.dto;

public record CartaoSalvoResponseDTO(
    Long id,
    String nomeTitular,
    String validade,
    String ultimos4
) {}
