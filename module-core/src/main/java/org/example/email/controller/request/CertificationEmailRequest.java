package org.example.email.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;

@Schema(description = "이메일 인증번호 발송 요청 객체")
public record CertificationEmailRequest(
  @Email
  String email) {
}
