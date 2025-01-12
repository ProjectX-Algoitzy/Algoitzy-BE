package org.example.domain.attendance.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import java.util.List;

@Schema(description = "출석부 수정 요청 객체")
public record UpdateAttendanceRequest(

  @Schema(description = "출석부 수정 요청 list")
  List<@Valid UpdateAttendanceDto> attendanceList

) {

}
