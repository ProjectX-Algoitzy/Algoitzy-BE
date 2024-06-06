package org.example.config.jpa;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Converter
public class StringListToStringConverter implements AttributeConverter<List<String>, String> {

  @Override
  public String convertToDatabaseColumn(List<String> stringList) {
    if (ObjectUtils.isEmpty(stringList)) {
      return null;
    }
    return String.join(",", stringList);
  }

  @Override
  public List<String> convertToEntityAttribute(String dbData) {
    if (!StringUtils.hasText(dbData)) {
      return Collections.emptyList();
    }
    return Arrays.asList(dbData.split(","));
  }
}
