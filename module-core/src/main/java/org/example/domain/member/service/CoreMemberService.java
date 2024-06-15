package org.example.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.member.Member;
import org.example.domain.member.controller.request.LoginRequest;
import org.example.domain.member.controller.request.RefreshAccessTokenRequest;
import org.example.domain.member.controller.response.LoginResponse;
import org.example.domain.member.controller.response.MemberInfoResponse;
import org.example.domain.member.enums.Role;
import org.example.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoreMemberService {

  private final MemberRepository memberRepository;
  private final CoreLoginService coreLoginService;
  private final CoreDetailMemberService coreDetailMemberService;

  public Member findById(Long memberId) {
    return memberRepository.findById(memberId)
      .orElseThrow(() -> new GeneralException(ErrorStatus.NOT_FOUND, "존재하지 않는 ID 입니다."));
  }

  public Member findByEmail(String email) {
    return memberRepository.findByEmail(email)
      .orElseThrow(() -> new GeneralException(ErrorStatus.NOT_FOUND, "존재하지 않는 Email 입니다."));
  }

  public LoginResponse login(Role role, LoginRequest request) {
    return coreLoginService.login(role, request);
  }

  /**
   * Access Token 재발급
   */
  public LoginResponse refreshAccessToken(RefreshAccessTokenRequest request) {
    return coreLoginService.refreshAccessToken(request);
  }

  /**
   * 로그인 멤버 정보
   */
  public MemberInfoResponse getMemberInfo() {
    return coreDetailMemberService.getMemberInfo();
  }
}
