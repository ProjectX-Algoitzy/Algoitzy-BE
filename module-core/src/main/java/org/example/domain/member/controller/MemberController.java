package org.example.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api_response.ApiResponse;
import org.example.domain.member.controller.request.LoginRequest;
import org.example.domain.member.controller.response.LoginResponse;
import org.example.domain.member.service.CoreMemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "MemberController", description = "멤버 관련 API")
public class MemberController {

  private final CoreMemberService coreMemberService;

  @PostMapping("/login")
  @Operation(summary = "로그인")
  public ApiResponse<LoginResponse> login(@RequestBody LoginRequest request) {
    return ApiResponse.onCreate(coreMemberService.login(request));
  }
}
