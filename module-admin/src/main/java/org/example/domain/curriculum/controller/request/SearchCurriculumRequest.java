package org.example.domain.curriculum.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "커리큘럼 목록 검색 요청 객체")
public record SearchCurriculumRequest(

  @Schema(description = "스터디 ID", type = "integer", format = "int64")
  Long studyId
) {

}
