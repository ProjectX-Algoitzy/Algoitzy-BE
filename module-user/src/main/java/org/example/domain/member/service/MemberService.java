package org.example.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.member.controller.request.CreateMemberRequest;
import org.example.domain.member.controller.request.FindEmailRequest;
import org.example.domain.member.controller.request.LoginRequest;
import org.example.domain.member.controller.request.AccessTokenRequest;
import org.example.domain.member.controller.response.MyPageInfoResponse;
import org.example.domain.member.controller.response.MyPageStudyResponse;
import org.example.email.controller.request.ValidateEmailRequest;
import org.example.domain.member.controller.request.ValidateHandleRequest;
import org.example.domain.sms.controller.request.ValidatePhoneNumberRequest;
import org.example.domain.member.controller.response.LoginResponse;
import org.example.domain.member.controller.response.MemberInfoResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final LoginService loginService;
  private final CreateMemberService createMemberService;
  private final DetailMemberService detailMemberService;

  public void createMember(CreateMemberRequest request) {
    createMemberService.createMember(request);
  }

  public void validateHandle(ValidateHandleRequest request) {
    createMemberService.validateHandle(request);
  }

  public void validateEmail(ValidateEmailRequest request) {
    createMemberService.validateEmail(request);
  }

  public void validatePhoneNumber(ValidatePhoneNumberRequest request) {
    createMemberService.validatePhoneNumber(request);
  }

  /**
   * 로그인
   */
  public LoginResponse login(LoginRequest request) {
    return loginService.login(request);
  }

  /**
   * 로그아웃
   */
  public void logout() {
    loginService.logout();
  }

  /**
   * Access Token 만료 임박 확인
   */
  public Boolean checkAccessToken(AccessTokenRequest request) {
    return loginService.checkAccessToken(request);
  }

  /**
   * Access Token 재발급
   */
  public LoginResponse refreshAccessToken(AccessTokenRequest request) {
    return loginService.refreshAccessToken(request);
  }

  /**
   * 로그인 멤버 정보
   */
  public MemberInfoResponse getMemberInfo() {
    return detailMemberService.getMemberInfo();
  }

  /**
   * 아이디(이메일 찾기)
   */
  public String findEmail(FindEmailRequest request) {
    return detailMemberService.findEmail(request);
  }

  /**
   * 마이페이지 멤버 정보
   */
  public MyPageInfoResponse getMyPageInfo(Long memberId) {
    return detailMemberService.getMyPageInfo(memberId);
  }

  public MyPageStudyResponse getMyPageStudy(Long memberId) {
    return detailMemberService.getMyPageStudy(memberId);
  }
}
