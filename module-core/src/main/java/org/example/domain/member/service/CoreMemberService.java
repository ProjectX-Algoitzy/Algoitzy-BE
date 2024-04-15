package org.example.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.member.Member;
import org.example.domain.member.controller.request.LoginRequest;
import org.example.domain.member.controller.response.LoginResponse;
import org.example.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoreMemberService {

  private final MemberRepository memberRepository;
  private final LoginService loginService;

  public Member findByEmail(String email) {
    return memberRepository.findByEmail(email)
      .orElseThrow(() -> new GeneralException(ErrorStatus.NOT_FOUND, "존재하지 않는 Email 입니다."));
  }

  public LoginResponse login(LoginRequest request) {
    return loginService.login(request);
  }
}
