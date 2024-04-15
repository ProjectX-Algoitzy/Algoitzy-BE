package org.example.domain.field.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import java.time.LocalDate;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public record CreateFieldRequest(

  @Schema(description = "문자형 필드", example = "소프트웨어학과")
  String stringField,

  @Schema(description = "날짜형 필드")
  LocalDate dateField
) {

  @AssertTrue(message = "문자형/날짜형 필드 중 하나만 존재해야 합니다.")
  @Schema(hidden = true)
  public boolean getFieldValidation() {
    boolean isStringField = StringUtils.hasText(stringField);
    boolean isDateField = !ObjectUtils.isEmpty(dateField);

    return ((!isStringField && isDateField)
      || (isStringField && !isDateField));
  }
}
