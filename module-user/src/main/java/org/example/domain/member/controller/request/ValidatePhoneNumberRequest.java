package org.example.domain.member.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record ValidatePhoneNumberRequest(

  @NotBlank
  @Schema(description = "핸드폰 번호", example = "01012341234")
  String phoneNumber,

  @NotBlank
  @Schema(description = "인증 코드", example = "1111")
  String code
) {

}
