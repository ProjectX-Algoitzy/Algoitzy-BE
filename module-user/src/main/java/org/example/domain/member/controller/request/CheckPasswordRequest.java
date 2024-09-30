package org.example.domain.member.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "비밀번호 확인 요청 객체")
public record CheckPasswordRequest(

  @NotBlank
  @Schema(description = "비밀번호")
  String password
) {

}
