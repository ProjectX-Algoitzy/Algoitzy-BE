package org.example.sms.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.example.validator.PhoneNumber;

@Schema(description = "핸드폰 번호 인증번호 발송 요청 객체")
public record CertificationPhoneNumberRequest(

  @PhoneNumber
  @Schema(description = "핸드폰 번호 인증번호 발송 요청 객체", example = "010-6751-2077")
  String phoneNumber
) {

}
