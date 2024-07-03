package org.example.domain.field.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "지원서 양식 객관식 문항 필드 응답 객체")
public class DetailFieldDto {

  @Schema(description = "필드 ID")
  private long fieldId;

  @Schema(description = "필드 내용")
  private String context;
}
