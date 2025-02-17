package org.example.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.member.controller.request.FindEmailRequest;
import org.example.domain.member.controller.request.LoginRequest;
import org.example.domain.member.controller.request.AccessTokenRequest;
import org.example.domain.member.controller.request.SearchMemberRequest;
import org.example.domain.member.controller.request.UpdateMemberRoleRequest;
import org.example.domain.member.controller.response.ListMemberResponse;
import org.example.domain.member.controller.response.LoginResponse;
import org.example.domain.member.controller.response.MemberInfoResponse;
import org.example.domain.member.service.MemberService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
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
    return ApiResponse.onSuccess(memberService.login(request));
  }

  @PostMapping("/logout")
  @Operation(summary = "로그아웃")
  public ApiResponse<Void> logout() {
    memberService.logout();
    return ApiResponse.onSuccess();
  }

  @PostMapping("/check-token")
  @Operation(summary = "Access Token 만료 임박 확인", description = "5분 이내 만료 시 true 반환")
  public ApiResponse<Boolean> checkAccessToken(@RequestBody @Valid AccessTokenRequest request) {
    return ApiResponse.onSuccess(memberService.checkAccessToken(request));
  }

  @PostMapping("/refresh-token")
  @Operation(summary = "Access Token 재발급")
  public ApiResponse<LoginResponse> refreshAccessToken(@RequestBody @Valid AccessTokenRequest request) {
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

  @PatchMapping("/role")
  @Operation(summary = "멤버 권한 변경")
  public ApiResponse<Void> updateMemberRole(@RequestBody @Valid UpdateMemberRoleRequest request) {
    memberService.updateMemberRole(request);
    return ApiResponse.onSuccess();
  }

  @GetMapping("/info")
  @Operation(summary = "로그인 멤버 정보")
  public ApiResponse<MemberInfoResponse> getLoginMemberInfo() {
    return ApiResponse.onSuccess(memberService.getMemberInfo());
  }

  @GetMapping("/find-email")
  @Operation(summary = "아이디(이메일) 찾기")
  public ApiResponse<String> findEmail(@ParameterObject @ModelAttribute @Valid FindEmailRequest request) {
    return ApiResponse.onSuccess(memberService.findEmail(request));
  }
}
