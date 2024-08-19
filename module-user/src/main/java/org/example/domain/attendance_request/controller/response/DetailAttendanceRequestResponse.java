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
@Schema(description = "현재 주차 출석 요청 조회 객체")
public class DetailAttendanceRequestResponse {

  @JsonIgnore
  Long attendanceRequestId;

  @Setter
  @Default
  @Schema(description = "문제 URL 목록")
  List<String> problemUrlList = new ArrayList<>();

  @Default
  @Schema(description = "블로그 URL")
  String blogUrl = "";

}
