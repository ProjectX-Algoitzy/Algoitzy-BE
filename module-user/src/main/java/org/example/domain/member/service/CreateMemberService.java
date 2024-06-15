package org.example.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.member.controller.request.CreateMemberRequest;
import org.example.domain.member.controller.request.ValidateEmailRequest;
import org.example.domain.member.controller.request.ValidateHandleRequest;
import org.example.domain.member.Member;
import org.example.domain.member.controller.request.ValidatePhoneNumberRequest;
import org.example.domain.member.enums.Role;
import org.example.domain.member.repository.MemberRepository;
import org.example.util.RedisUtils;
import org.example.util.http_request.HttpRequest;
import org.example.util.http_request.Url;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CreateMemberService {

  private final MemberRepository memberRepository;
  private final PasswordEncoder encoder;
  private final RedisUtils redisUtils;

  /**
   * 회원 가입
   */
  public void createMember(CreateMemberRequest request) {
    if (memberRepository.findByEmail(request.email()).isPresent()) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "이미 가입된 이메일입니다.");
    }

    memberRepository.save(
      Member.builder()
        .profileUrl(request.profileUrl())
        .email(request.email())
        .password(encoder.encode(request.password()))
        .name(request.name())
        .grade(request.grade())
        .major(request.major())
        .handle(request.handle())
        .phoneNumber(request.phoneNumber())
        .role(Role.ROLE_USER)
        .build());
  }

  /**
   * 이메일 인증
   */
  public void validateEmail(ValidateEmailRequest request) {
    String code = redisUtils.getValue(request.email());
    if (code == null) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "해당 이메일과 매칭되는 인증코드가 없습니다.");
    }

    if (!code.equals(request.code())) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "인증코드가 일치하지 않습니다.");
    }
    redisUtils.delete(request.email());
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
   * 휴대전화 인증
   */
  public void validatePhoneNumber(ValidatePhoneNumberRequest request) {
    String code = redisUtils.getValue(request.phoneNumber());
    if (code == null) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "해당 번호와 매칭되는 인증코드가 없습니다.");
    }

    if (!code.equals(request.code())) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "인증코드가 일치하지 않습니다.");
    }
    redisUtils.delete(request.phoneNumber());
  }
}
