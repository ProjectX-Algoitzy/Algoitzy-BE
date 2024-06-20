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
@Schema(description = "정규 스터디 정보 응답 객체")
public class RegularStudyInfoResponse {

  @Schema(description = "스터디 대표 이미지 URL")
  String profileUrl;

  @Schema(description = "스터디 이름")
  String studyName;

  @Schema(description = "현재 스터디원 수")
  long memberCount;

  @Schema(description = "스터디장 프로필 이미지")
  String leaderProfileUrl;

  @Schema(description = "스터디장 이름")
  String leaderName;

  @Schema(description = "최초 제작일")
  LocalDateTime createdTime;

  @Schema(description = "스터디원 역할")
  String memberRole;
}
