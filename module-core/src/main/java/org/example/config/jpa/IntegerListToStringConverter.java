package org.example.config.jpa;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Converter
public class IntegerListToStringConverter implements AttributeConverter<List<Integer>, String> {

  @Override
  public String convertToDatabaseColumn(List<Integer> integerList) {
    if (ObjectUtils.isEmpty(integerList)) {
      return null;
    }
    return integerList.stream().map(String::valueOf).collect(Collectors.joining(","));
  }

  @Override
  public List<Integer> convertToEntityAttribute(String dbData) {
    if (!StringUtils.hasText(dbData)) {
      return Collections.emptyList();
    }
    return Arrays.stream(dbData.split(",")).map(Integer::parseInt).toList();
  }
}
