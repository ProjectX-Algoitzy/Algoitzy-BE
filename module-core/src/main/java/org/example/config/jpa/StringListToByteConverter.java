package org.example.config.jpa;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

@Slf4j
@Converter
public class StringListToByteConverter implements AttributeConverter<List<String>, byte[]> {

    private static final ObjectMapper mapper = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);

    // List 형식의 데이터를 DB의 byte[]로 저장
    @Override
    public byte[] convertToDatabaseColumn(List<String> attribute) {
        try {
            if (ObjectUtils.isEmpty(attribute)) {
                return null;
            }
            return mapper.writeValueAsBytes(attribute);
        } catch (JsonProcessingException e) {
            log.debug("ByteListConverter.convertToDatabaseColumn exception occur attribute : {}", attribute.toString());
            throw new RuntimeException("Error converting List<String> to byte[]", e);
        }
    }

    @Override
    public List<String> convertToEntityAttribute(byte[] bytes) {
        return null;
    }

}
