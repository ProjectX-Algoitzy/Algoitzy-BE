package org.example.domain.attendance_request.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "출석 인증 내역 목록 조회 객체")
public class ListAttendanceRequestResponse {

  @Default
  @Schema(description = "출석 인증 내역 목록")
  private List<ListAttendanceRequestDto> attendanceRequestList = new ArrayList<>();

}
