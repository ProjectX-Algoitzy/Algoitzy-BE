package org.example.email.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import org.example.email.enums.EmailType;

@Schema(description = "이메일 인증번호 발송 요청 객체")
public record SendEmailRequest(

  @Schema(description = "이메일 유형",
    allowableValues = {"CERTIFICATION", "DOCUMENT_PASS", "DOCUMENT_FAIL", "INTERVIEW", "FAIL", "PASS"})
  String type,

  @Email
  @Schema(description = "이메일", example = "engus525@naver.com")
  String email

) {

  @AssertTrue(message = "존재하지 않는 이메일 유형입니다.")
  @Schema(hidden = true)
  public boolean getEmailTypeValidate() {
    return EmailType.exist(type);
  }
}
