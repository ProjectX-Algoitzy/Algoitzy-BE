package org.example.domain.member.controller;

import static org.example.domain.member.enums.Role.ROLE_ADMIN;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.member.controller.request.LoginRequest;
import org.example.domain.member.controller.request.RefreshAccessTokenRequest;
import org.example.domain.member.controller.request.SearchMemberRequest;
import org.example.domain.member.controller.response.ListMemberResponse;
import org.example.domain.member.controller.response.LoginResponse;
import org.example.domain.member.controller.response.MemberInfoResponse;
import org.example.domain.member.service.MemberService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

  @PostMapping("/login")
  @Operation(summary = "로그인")
  public ApiResponse<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
    return ApiResponse.onSuccess(memberService.login(ROLE_ADMIN, request));
  }

  @PostMapping("/logout")
  @Operation(summary = "로그아웃")
  public ApiResponse<Void> logout() {
    memberService.logout();
    return ApiResponse.onSuccess();
  }

  @PostMapping("/refresh-token")
  @Operation(summary = "Access Token 재발급")
  public ApiResponse<LoginResponse> refreshAccessToken(@RequestBody @Valid RefreshAccessTokenRequest request) {
    return ApiResponse.onSuccess(memberService.refreshAccessToken(request));
  }

  @GetMapping("/admin")
  @Operation(summary = "관리자 목록 조회")
  public ApiResponse<ListMemberResponse> getAdminMemberList() {
    return ApiResponse.onSuccess(memberService.getAdminMemberList());
  }

  @GetMapping("/user")
  @Operation(summary = "스터디원 목록 조회")
  public ApiResponse<ListMemberResponse> getUserMemberList(
    @ParameterObject @ModelAttribute @Valid SearchMemberRequest request
  ) {
    return ApiResponse.onSuccess(memberService.getUserMemberList(request));
  }

  @PatchMapping("/{member-id}/role")
  @Operation(summary = "유저 역할 변경")
  public ApiResponse<LoginResponse> updateMemberRole(@PathVariable("member-id") Long memberId) {
    memberService.updateMemberRole(memberId);
    return ApiResponse.onSuccess();
  }

  @GetMapping("/info")
  @Operation(summary = "로그인 멤버 정보")
  public ApiResponse<MemberInfoResponse> getLoginMemberInfo() {
    return ApiResponse.onSuccess(memberService.getMemberInfo());
  }
}
