package org.example.domain.member.controller;

import static org.example.domain.member.enums.Role.ROLE_ADMIN;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.member.controller.request.LoginRequest;
import org.example.domain.member.controller.response.LoginResponse;
import org.example.domain.member.service.CoreMemberService;
import org.example.domain.member.service.MemberService;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Tag(name = "MemberController", description = "[ADMIN] 멤버 관련 API")
public class MemberController {

  private final MemberService memberService;
  private final CoreMemberService coreMemberService;

  @PostMapping("/login")
  @Operation(summary = "로그인")
  public ApiResponse<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
    return ApiResponse.onSuccess(coreMemberService.login(ROLE_ADMIN, request));
  }

  @PatchMapping("/{member-id}/role")
  @Operation(summary = "유저 역할 변경")
  public ApiResponse<LoginResponse> updateMemberRole(@PathVariable("member-id") Long memberId) {
    memberService.updateMemberRole(memberId);
    return ApiResponse.onSuccess();
  }
}
