package org.example.domain.study.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "정규 스터디 수정 요청 객체")
public record UpdateRegularStudyRequest(

  @NotNull
  @Schema(description = "스터디 ID")
  Long studyId,

  @Schema(description = "스터디 대표 이미지 URL")
  String profileUrl,

  @Schema(description = "스터디 이름")
  String name,

  @Schema(description = "내용(에디터)")
  String content

) {

}
