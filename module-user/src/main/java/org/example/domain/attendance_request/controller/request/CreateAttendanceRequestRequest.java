package org.example.domain.attendance_request.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "출석 요청 생성 요청 객체")
public record CreateAttendanceRequestRequest(

  @Schema(description = "스터디 ID")
  Long studyId,

  @Schema(description = "문제 URL 목록")
  List<String> problemUrlList,

  String blogUrl

) {

}
