package org.example.domain.member.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateMemberRequest(

  @Email
  @Schema(description = "이메일", example = "engus525@naver.com")
  String email,
  @Schema(description = "비밀번호")
  String password,
  @Schema(description = "비밀번호 확인")
  String checkPassword,

  @NotBlank
  @Schema(description = "이름", example = "김두현")
  String name,

  @NotBlank
  @Schema(description = "백준 핸들", example = "engus525")
  String handle,

  @NotBlank
  @Schema(description = "핸드폰 번호", example = "010-1234-1234")
  String phoneNumber

) {

  @AssertTrue(message = "비밀번호가 일치하지 않습니다.")
  @Schema(hidden = true)
  public boolean getPasswordValidate() {
    return password.equals(checkPassword);
  }

}
