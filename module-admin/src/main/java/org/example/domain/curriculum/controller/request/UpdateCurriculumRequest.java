package org.example.domain.curriculum.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "커리큘럼 수정 요청 객체")
public record UpdateCurriculumRequest(

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
