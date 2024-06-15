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
@Schema(description = "자율 스터디 상세 응답 객체")
public class DetailTempStudyResponse {

  @Schema(description = "스터디 대표 이미지 URL")
  String profileUrl;

  @Schema(description = "스터디 이름")
  String studyName;

  @Schema(description = "현재 스터디원 수")
  long memberCount;

  @Schema(description = "내용")
  String content;

  @Schema(description = "모집 인원")
  int memberLimit;

  @Schema(description = "스터디장 프로필 이미지")
  String leaderProfileUrl;

  @Schema(description = "스터디장 이름")
  String leaderName;

  @Schema(description = "최초 제작일")
  LocalDateTime createdTime;

  @Schema(description = "주제")
  String subject;

  @Schema(description = "대상")
  String target;

  @Schema(description = "스터디원 역할")
  String memberRole;

}
