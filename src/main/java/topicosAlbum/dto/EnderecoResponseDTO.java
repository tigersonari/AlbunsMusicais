package topicosAlbum.dto;

public record EnderecoResponseDTO(
    Long id,
    String rua,
    String numero,
    String complemento,
    String bairro,
    String cidade,
    String uf,
    String cep
) {}
