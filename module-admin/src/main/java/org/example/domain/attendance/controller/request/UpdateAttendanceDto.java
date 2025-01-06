package org.example.domain.attendance.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.example.domain.attendance.enums.AttendanceType;

@Schema(description = "출석부 수정 요청 DTO")
public record UpdateAttendanceDto(

  @Schema(description = "출석부 ID")
  long attendanceId,

  @Schema(description = "출석 유형")
  AttendanceType attendanceType

) {

}
