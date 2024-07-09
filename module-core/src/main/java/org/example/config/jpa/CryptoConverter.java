package org.example.config.jpa;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.example.util.CryptoUtils;
import org.springframework.util.StringUtils;

@Converter
public class CryptoConverter implements AttributeConverter<String, String> {

  @Override
  public String convertToDatabaseColumn(String str) {
    if (!StringUtils.hasText(str)) {
      return null;
    }
    return CryptoUtils.encode(str);
  }

  @Override
  public String convertToEntityAttribute(String dbData) {
    if (!StringUtils.hasText(dbData)) {
      return null;
    }
    return CryptoUtils.decode(dbData);
  }
}
