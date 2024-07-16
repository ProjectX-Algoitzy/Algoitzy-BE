package org.example.domain.attendance.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "출석부 목록 응답 객체")
public class ListAttendanceResponse {

  private List<ListAttendanceDto> attendanceList = new ArrayList<>();
}
