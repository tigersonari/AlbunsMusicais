package topicosAlbum.model.converterjpa;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import topicosAlbum.model.TipoVersao;

@Converter(autoApply = true)
public class TipoVersaoConverter implements AttributeConverter<TipoVersao, Long>{

    @Override
    public Long convertToDatabaseColumn(TipoVersao tipoVersao) {
        return (tipoVersao == null) ? null : tipoVersao.ID;
    }

    @Override
    public TipoVersao convertToEntityAttribute(Long id) {
        return TipoVersao.valueOf(id);
    }
    
}

