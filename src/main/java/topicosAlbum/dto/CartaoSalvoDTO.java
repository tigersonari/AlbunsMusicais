package topicosAlbum.dto;

public record CartaoSalvoDTO(
    String nomeTitular,
    String numero,  // número completo enviado apenas aqui
    String validade // MM/AA
) {}
