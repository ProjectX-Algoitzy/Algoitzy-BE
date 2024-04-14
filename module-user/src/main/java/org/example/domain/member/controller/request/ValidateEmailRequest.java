package org.example.domain.member.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ValidateEmailRequest(

  @Email
  @Schema(description = "이메일", example = "engus525@naver.com")
  String email,

  @NotBlank
  @Schema(description = "인증 코드", example = "1111")
  String code
) {

}
