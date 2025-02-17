package org.example.domain.application.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.util.DateUtils;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "지원서 양식 목록 응답 객체")
public class ListApplicationByGenerationDto {

  @Schema(description = "지원서 ID")
  private Long applicationId;

  @Schema(description = "지원서 제목")
  private String title;

  @Schema(description = "대상 스터디 이름")
  private String studyName;

  @Schema(description = "대상 스터디 이미지")
  private String studyProfileUrl;

  @Schema(description = "확정 여부")
  private boolean confirmYN;

  @Schema(description = "최초 제작자")
  private String createdName;

  @Schema(description = "최초 제작일")
  private LocalDateTime createdTime;

  @Schema(description = "최종 수정자 프로필 이미지")
  private String updatedMemberProfileUrl;

  @Schema(description = "최종 수정자")
  private String updatedName;

  private LocalDateTime updatedTime;

  @Schema(description = "최종 수정일")
  public String getUpdatedTime() {
    return DateUtils.getUpdatedTime(updatedTime);
  }
}
