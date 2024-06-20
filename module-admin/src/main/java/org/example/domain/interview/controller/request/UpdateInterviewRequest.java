package org.example.domain.interview.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Schema(description = "면접 일정 수정 요청 객체")
public record UpdateInterviewRequest(

  @NotNull
  @Schema(description = "면접 ID")
  Long interviewId,

  @NotBlank
  @Length(min = 8, max = 8)
  @Schema(description = "면접 시간", example = "05251200")
  String time

) {

}
