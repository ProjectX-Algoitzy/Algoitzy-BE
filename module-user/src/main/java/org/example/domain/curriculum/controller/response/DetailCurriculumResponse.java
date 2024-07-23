package org.example.domain.curriculum.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "커리큘럼 상세 조회 응답 객체")
public class DetailCurriculumResponse {

  @Schema(description = "커리큘럼 제목")
  private String title;

  @Schema(description = "주차")
  private int week;

  @Schema(description = "내용")
  private String content;

  @Schema(description = "대상 스터디 ID")
  private long studyId;

  @Schema(description = "대상 스터디 이름")
  private String studyName;

}
