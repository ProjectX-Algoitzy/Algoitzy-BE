package org.example.domain.study.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "자율 스터디 생성 요청 객체")
public record CreateTempStudyRequest(

  @Schema(description = "스터디 대표 이미지 URL")
  String profileUrl,

  @NotBlank
  @Schema(description = "스터디 이름")
  String name,

  @NotBlank
  @Schema(description = "내용(에디터)")
  String content

) {

}
