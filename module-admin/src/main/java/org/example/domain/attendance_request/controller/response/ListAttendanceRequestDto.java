package org.example.domain.attendance_request.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "출석 인증 내역 목록 조회 DTO")
public class ListAttendanceRequestDto {

  @JsonIgnore
  private Long attendanceRequestId;

  @Schema(description = "주차")
  private Integer week;

  @Setter
  @Default
  @Schema(description = "문제 URL 목록")
  private List<String> problemUrlList = new ArrayList<>();

  @Schema(description = "블로그 URL")
  private String blogUrl;

}
