package org.example.domain.member.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.example.validator.PhoneNumber;

@Schema(description = "멤버 수정 요청 객체")
public record UpdateMemberRequest(

  @Schema(description = "프로필 이미지")
  String profileUrl,

  @Schema(description = "이름", example = "김두현")
  String name,

  @Min(1)
  @Max(4)
  @Schema(description = "학년")
  Integer grade,

  @Schema(description = "학과", example = "소프트웨어학과")
  String major,

  @Schema(description = "백준 핸들", example = "engus525")
  String handle,

  @Schema(description = "비밀번호")
  String password,

  @Schema(description = "비밀번호 확인")
  String checkPassword,

  @PhoneNumber
  @Schema(description = "핸드폰 번호", example = "01012341234")
  String phoneNumber

) {

  public UpdateMemberRequest {
    phoneNumber = phoneNumber.replace("-", "");
  }

  @AssertTrue(message = "비밀번호가 일치하지 않습니다.")
  @Schema(hidden = true)
  private boolean getPasswordValidate() {
    return password.equals(checkPassword);
  }

}
