package org.example.domain.study.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "자율 스터디 목록 응답 객체")
public class ListTempStudyDto {

  @Schema(description = "스터디 ID")
  long studyId;

  @Schema(description = "스터디 대표 이미지 URL")
  String profileUrl;

  @Schema(description = "스터디 이름")
  String studyName;

  @Schema(description = "현재 스터디원 수")
  long memberCount;

  @Schema(description = "모집 인원")
  int memberLimit;

  @Schema(description = "스터디장 이름")
  String leaderName;

  @Schema(description = "최초 제작일")
  LocalDateTime createdTime;
}
