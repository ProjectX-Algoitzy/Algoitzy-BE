package org.example.domain.curriculum.controller.response;


import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.util.ValueUtils;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "커리큘럼 목록 조회 응답 dto")
public class ListCurriculumDto {

  @Schema(description = "커리큘럼 ID")
  private long curriculumId;

  @Schema(description = "커리큘럼 제목")
  private String title;

  @Schema(description = "대상 스터디 이름")
  private String studyName;

  @Schema(description = "최초 제작자")
  private String createdName;

  @Schema(description = "최초 제작일")
  private LocalDateTime createdTime;

  @Schema(description = "최종 수정자")
  private String updatedName;

  private LocalDateTime updatedTime;

  @Schema(description = "최종 수정일")
  public String getUpdatedTime() {
    return ValueUtils.getUpdatedTime(updatedTime);
  }
}
