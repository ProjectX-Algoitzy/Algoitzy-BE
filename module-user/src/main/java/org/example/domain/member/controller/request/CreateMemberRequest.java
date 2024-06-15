package org.example.domain.member.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "회원가입 요청 객체")
public record CreateMemberRequest(

  @Schema(description = "프로필 이미지")
  String profileUrl,

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

  @NotNull
  @Min(1)
  @Max(5)
  @Schema(description = "학년")
  int grade,

  @NotBlank
  @Schema(description = "학과", example = "소프트웨어학과")
  String major,

  @NotBlank
  @Schema(description = "백준 핸들", example = "engus525")
  String handle,

  @NotBlank
  @Schema(description = "핸드폰 번호", example = "01012341234")
  String phoneNumber

) {

  public CreateMemberRequest {
    // todo 기본 프로필 이미지
    phoneNumber = phoneNumber.replace("-", "");
  }

  @AssertTrue(message = "비밀번호가 일치하지 않습니다.")
  @Schema(hidden = true)
  public boolean getPasswordValidate() {
    return password.equals(checkPassword);
  }

  @AssertTrue(message = "핸드폰 번호를 확인해주세요.")
  @Schema(hidden = true)
  public boolean getPhoneNumberValidate() {
    System.out.println("phoneNumber = " + phoneNumber);
    return phoneNumber.length() == 11 && phoneNumber.matches("[0-9]+");
  }

}
