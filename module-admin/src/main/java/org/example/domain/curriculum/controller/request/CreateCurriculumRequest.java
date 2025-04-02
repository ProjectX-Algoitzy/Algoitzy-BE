package org.example.domain.curriculum.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "커리큘럼 생성 요청 객체")
public record CreateCurriculumRequest(

  @NotNull
  @Schema(description = "커리큘럼 대상 스터디 ID")
  Long studyId,

  @Schema(description = "커리큘럼 제목")
  String title,

  @Schema(description = "커리큘럼 주차")
  Integer week,

  @Schema(description = "커리큘럼 내용(에디터)")
  String content
) {

}
