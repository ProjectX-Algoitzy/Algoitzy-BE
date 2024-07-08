package org.example.domain.attendance_request.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import java.util.List;
import org.example.util.ArrayUtils;

@Schema(description = "출석 요청 생성 요청 객체")
public record CreateAttendanceRequestRequest(

  @Schema(description = "스터디 ID")
  Long studyId,

  @Schema(description = "문제 URL 목록")
  List<String> problemUrlList,

  String blogUrl

) {

  @AssertTrue(message = "전체 URL을 입력해주세요.")
  @Schema(hidden = true)
  private boolean getProblemUrlListValidate() {
    return problemUrlList.stream()
      .allMatch(problemUrl -> problemUrl.contains("www.")) && blogUrl.contains("www.");
  }

  @AssertTrue(message = "중복된 URL이 존재합니다.")
  @Schema(hidden = true)
  private boolean getListUniqueValidate() {
    return ArrayUtils.isUniqueArray(problemUrlList);
  }
}
