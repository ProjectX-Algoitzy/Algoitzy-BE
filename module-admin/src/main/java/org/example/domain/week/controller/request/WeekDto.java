package org.example.domain.week.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;

@Getter
@Schema(description = "기수 갱신 주차별 요청 객체")
public class WeekDto {

  @Min(1)
  @Max(8)
  @Schema(description = "주차")
  private Integer week;

  @NotNull
  @Schema(description = "주차 시작 일자")
  private LocalDate startTime;
}
