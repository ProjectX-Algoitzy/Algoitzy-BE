package org.example.domain.interview.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "면접 일정 수정 요청 객체")
public record UpdateInterviewRequest(

  @NotNull
  @Schema(description = "면접 ID")
  Long interviewId,

  @NotBlank
  @Schema(description = "면접 시간")
  String time

) {

  @AssertTrue(message = "MM월 DD일 오전/오후 HH시 MM분 형식이어야 합니다.")
  public boolean getTimeValidate() {
    return time.contains("월")
      && time.contains("일")
      && (time.contains("오후") || time.contains("오전"))
      && time.contains("시")
      && time.contains("분");
  }

}
