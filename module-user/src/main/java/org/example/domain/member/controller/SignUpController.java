package org.example.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.member.controller.request.CreateMemberRequest;
import org.example.domain.member.controller.request.ValidateEmailRequest;
import org.example.domain.member.controller.request.ValidateHandleRequest;
import org.example.domain.member.controller.request.ValidatePhoneNumberRequest;
import org.example.domain.member.service.MemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sign-up")
@RequiredArgsConstructor
@Tag(name = "SignUpController", description = "회원가입 관련 API")
public class SignUpController {

  private final MemberService memberService;

  @PostMapping()
  @Operation(summary = "회원 가입")
  public ApiResponse<Void> createMember(@RequestBody CreateMemberRequest request) {
    memberService.createMember(request);
    return ApiResponse.onCreate();
  }

  @PostMapping("/email")
  @Operation(summary = "이메일 인증")
  public ApiResponse<Void> validateEmail(@RequestBody ValidateEmailRequest request) {
    memberService.validateEmail(request);
    return ApiResponse.onSuccess();
  }

  @PostMapping("/handle")
  @Operation(summary = "백준 유효 계정 인증")
  public ApiResponse<Void> validateHandle(@RequestBody ValidateHandleRequest request) {
    memberService.validateHandle(request);
    return ApiResponse.onSuccess();
  }

  @PostMapping("/phone-number")
  @Operation(summary = "휴대전화 인증")
  public ApiResponse<Void> validatePhoneNumber(@RequestBody ValidatePhoneNumberRequest request) {
    memberService.validatePhoneNumber(request);
    return ApiResponse.onSuccess();
  }

}
