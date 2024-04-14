package org.example.domain.member.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

  @Email
  @Schema(description = "이메일", example = "engus525@naver.com")
  String email,

  @Schema(description = "비밀번호")
  @NotBlank
  String password
) {

}
