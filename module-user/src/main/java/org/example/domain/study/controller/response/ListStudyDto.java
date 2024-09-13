package org.example.domain.study.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.study.enums.StudyType;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "스터디 목록 응답 객체")
public class ListStudyDto {

  @Schema(description = "스터디 ID")
  private long studyId;

  @Schema(description = "스터디 대표 이미지 URL")
  private String profileUrl;

  @Schema(description = "스터디 이름")
  private String studyName;

  @Schema(description = "스터디 유형")
  private String studyType;

  @Schema(description = "현재 스터디원 수")
  private long memberCount;

  @Schema(description = "스터디장 프로필 이미지")
  private String leaderProfileUrl;

  @Schema(description = "스터디장 이름")
  private String leaderName;

  @Schema(description = "최초 제작일")
  private LocalDateTime createdTime;

  public void updateType(String studyType) {
    this.studyType = StudyType.valueOf(studyType).getName();
  }
}
