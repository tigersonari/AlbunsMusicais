package topicosAlbum.model.converterjpa;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import topicosAlbum.model.Formato;

@Converter(autoApply = true)
public class FormatoConverter implements AttributeConverter<Formato, Long>{

    @Override
    public Long convertToDatabaseColumn(Formato formato) {
        return (formato == null) ? null : formato.ID;
    }

    @Override
    public Formato convertToEntityAttribute(Long id) {
        return Formato.valueOf(id);
    }
    
}

