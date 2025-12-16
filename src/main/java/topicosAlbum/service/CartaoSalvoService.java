package topicosAlbum.service;

import java.util.List;

import topicosAlbum.dto.NovoCartaoDTO;
import topicosAlbum.model.CartaoSalvo;

public interface CartaoSalvoService {

    List<CartaoSalvo> listarDoUsuario(Long idUsuario);

    CartaoSalvo buscarDoUsuario(Long idCartao, Long idUsuario);

    CartaoSalvo criarParaUsuario(NovoCartaoDTO dto, Long idUsuario);
}
