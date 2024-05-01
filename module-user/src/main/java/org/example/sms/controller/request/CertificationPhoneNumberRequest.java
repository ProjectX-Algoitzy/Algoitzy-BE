package org.example.sms.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "핸드폰 번호 인증번호 발송 요청 객체")
public record CertificationPhoneNumberRequest(
  String phoneNumber
) {

}
