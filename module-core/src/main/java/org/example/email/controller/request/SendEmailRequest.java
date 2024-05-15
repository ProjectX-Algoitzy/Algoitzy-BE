package org.example.email.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import org.example.email.enums.EmailType;

@Schema(description = "이메일 발송 요청 객체")
public record SendEmailRequest(

  @Schema(description = "이메일 유형",
    allowableValues = {"CERTIFICATION", "DOCUMENT_PASS", "DOCUMENT_FAIL", "INTERVIEW", "FAIL", "PASS"})
  String type,

  @NotEmpty
  @Schema(description = "이메일", examples = {"engus525@naver.com"})
  List<@Email String> emailList

) {

  @AssertTrue(message = "존재하지 않는 이메일 유형입니다.")
  @Schema(hidden = true)
  public boolean getEmailTypeValidate() {
    return EmailType.exist(type);
  }

  @AssertTrue(message = "인증번호 발송 대상은 하나여야 합니다.")
  @Schema(hidden = true)
  public boolean getCertificationValidate() {
    if (type.equals("CERTIFICATION")) {
      return emailList.size() == 1;
    }
    return true;
  }

  // todo 중복 검증
}
