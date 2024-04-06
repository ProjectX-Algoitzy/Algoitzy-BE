package org.example.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.member.Member;
import org.example.domain.member.controller.request.CreateMemberRequest;
import org.example.domain.member.controller.request.LoginRequest;
import org.example.domain.member.controller.request.ValidateEmailRequest;
import org.example.domain.member.controller.request.ValidateHandleRequest;
import org.example.domain.member.controller.response.LoginResponse;
import org.example.domain.member.enums.Role;
import org.example.domain.member.repository.MemberRepository;
import org.example.security.jwt.JwtToken;
import org.example.security.jwt.JwtTokenProvider;
import org.example.util.http_request.HttpRequest;
import org.example.util.http_request.Url;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateMemberService {

  private final MemberRepository memberRepository;
  private final PasswordEncoder encoder;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final JwtTokenProvider jwtTokenProvider;

  /**
   * 회원 가입
   */
  public void createMember(CreateMemberRequest request) {
    memberRepository.save(
      Member.builder()
        .email(request.email())
        .password(encoder.encode(request.password()))
        .name(request.name())
        .handle(request.handle())
        .phoneNumber(request.phoneNumber())
        .role(Role.ROLE_USER)
        .build());
  }

  /**
   * 이메일 인증
   */
  public void validateEmail(ValidateEmailRequest request) {

  }

  /**
   * 백준 유효 계정 인증
   */
  public void validateHandle(ValidateHandleRequest request) {
    ResponseEntity<String> response = HttpRequest.getRequest(Url.BAEKJOON_USER.getBaekjoonUserUrl(request.handle()), String.class);
    if (!response.getStatusCode().is2xxSuccessful()) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "백준 핸들이 유효하지 않습니다.");
    }
  }

  /**
   * 로그인
   */
  public LoginResponse login(LoginRequest request) {
    UsernamePasswordAuthenticationToken authenticationToken =
      new UsernamePasswordAuthenticationToken(request.email(), request.password());
    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    JwtToken jwtToken = jwtTokenProvider.generateToken(authentication, request.email());

    return LoginResponse
      .builder()
      .accessToken(jwtToken.accessToken())
      .build();
  }

}