package org.example.domain.application.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "지원서 양식 목록 검색 요청 객체")
public record SearchApplicationRequest(

  @NotNull
  @Schema(description = "스터디 기수")
  int generation
) {

}
