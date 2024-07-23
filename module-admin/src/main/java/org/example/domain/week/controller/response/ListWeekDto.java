package org.example.domain.week.controller.response;

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
@Schema(description = "기수별 주차 목록 응답 객체")
public class ListWeekDto {

  @Schema(description = "주차")
  private int week;

  @Schema(description = "주차 시작 일자")
  private LocalDateTime startTime;

  @Schema(description = "주차 종료 일자")
  private LocalDateTime endTime;
}
