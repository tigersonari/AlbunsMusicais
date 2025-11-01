package topicosAlbum.dto;

import topicosAlbum.model.Empresa;

public record EmpresaResponseDTO(
    Long id,
    String nomeEmpresa,
    String cnpj,
    String localizacao,
    String contato
) {
    public static EmpresaResponseDTO valueOf(Empresa empresa) {
        return new EmpresaResponseDTO(
            empresa.getId(),
            empresa.getNomeEmpresa(),
            empresa.getCnpj(),
            empresa.getLocalizacao(),
            empresa.getContato()
        );
    }
}
