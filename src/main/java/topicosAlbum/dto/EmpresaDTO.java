package topicosAlbum.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EmpresaDTO(

    @NotBlank(message = "O nome da empresa é obrigatório.")
    String nomeEmpresa,

    @NotBlank(message = "O CNPJ é obrigatório.")
    @Pattern(regexp = "\\d{14}", message = "CNPJ deve conter 14 dígitos numéricos.")
    String cnpj,

    @NotBlank(message = "A localização é obrigatória.")
    String localizacao,

    @NotBlank(message = "O contato é obrigatório.")
    @Size(min = 5, message = "O contato deve ser mais detalhado.")
    String contato

) {}
