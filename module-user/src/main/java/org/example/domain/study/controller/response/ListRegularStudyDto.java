package org.example.domain.study.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "정규 스터디 목록 응답 dto")
public class ListRegularStudyDto {

  @Schema(description = "스터디 ID")
  private long studyId;

  @Schema(description = "스터디 대표 이미지 URL")
  private String profileUrl;

  @Schema(description = "스터디 이름")
  private String name;

  @Schema(description = "현재 스터디원 수")
  long memberCount;
}
