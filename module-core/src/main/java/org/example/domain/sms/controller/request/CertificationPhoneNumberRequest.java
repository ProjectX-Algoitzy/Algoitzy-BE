package org.example.domain.sms.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.example.validator.PhoneNumber;

@Schema(description = "핸드폰 번호 인증번호 발송 요청 객체")
public record CertificationPhoneNumberRequest(

  @PhoneNumber
  @Schema(description = "핸드폰 번호 인증번호 발송 요청 객체", example = "01067512077")
  String phoneNumber,

  @Schema(description = "사용자 랜덤 문자열 id")
  String userRandomId
) {



}
