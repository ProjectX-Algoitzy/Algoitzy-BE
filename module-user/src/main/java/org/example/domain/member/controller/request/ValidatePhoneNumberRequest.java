package org.example.domain.member.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record ValidatePhoneNumberRequest(

  @NotBlank
  @Schema(description = "이메일", example = "engus525@naver.com")
  String phoneNumber,

  @NotBlank
  @Schema(description = "인증 코드", example = "1111")
  String code
) {

}
