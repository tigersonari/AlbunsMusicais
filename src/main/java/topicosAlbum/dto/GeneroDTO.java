package topicosAlbum.dto;

import jakarta.validation.constraints.NotBlank;

public record GeneroDTO(

    @NotBlank(message = "O nome do gênero é obrigatório.")
    String nomeGenero,

    @NotBlank(message = "A descrição é obrigatória.")
    String descricao

) {}
