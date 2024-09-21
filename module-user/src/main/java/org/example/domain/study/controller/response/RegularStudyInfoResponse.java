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
  private String profileUrl;

  @Schema(description = "스터디 이름")
  private String studyName;

  @Schema(description = "현재 스터디원 수")
  private long memberCount;

  @Schema(description = "지원서 ID")
  private Long applicationId;

  @Schema(description = "답변 ID")
  private Long answerId;

  @Schema(description = "지원서 작성 여부")
  private boolean answerYN;

  @Schema(description = "최초 제작일")
  private LocalDateTime createdTime;

  @Schema(description = "스터디원 역할")
  private String memberRole;
}
