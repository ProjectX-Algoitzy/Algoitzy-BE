package org.example.domain.attendance.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "출석부 목록 응답 DTO")
public class ListAttendanceDto {

  @Schema(description = "출석부 ID")
  private Long attendanceId;

  @Schema(description = "주차")
  private Integer week;

  @Schema(description = "이름")
  private String name;

  @Schema(description = "백준 닉네임")
  private String handle;

  @Schema(description = "문제 할당량 충족 여부")
  private Boolean problemYN;

  @Schema(description = "블로그 포스팅 여부")
  private Boolean blogYN;

  @Schema(description = "모의테스트 여부")
  private Boolean workbookYN;
}
