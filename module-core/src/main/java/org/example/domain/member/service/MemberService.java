package org.example.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.member.controller.request.LoginRequest;
import org.example.domain.member.controller.response.LoginResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final LoginService loginService;

  public LoginResponse login(LoginRequest request) {
    return loginService.login(request);
  }
}
