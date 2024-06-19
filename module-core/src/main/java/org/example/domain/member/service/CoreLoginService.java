package org.example.domain.member.service;

import static org.example.domain.member.enums.Role.ROLE_ADMIN;
import static org.example.domain.member.enums.Role.ROLE_USER;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.member.Member;
import org.example.domain.member.controller.request.LoginRequest;
import org.example.domain.member.controller.request.RefreshAccessTokenRequest;
import org.example.domain.member.controller.response.LoginResponse;
import org.example.domain.member.enums.Role;
import org.example.domain.member.repository.MemberRepository;
import org.example.security.jwt.JwtToken;
import org.example.security.jwt.JwtTokenProvider;
import org.example.util.RedisUtils;
import org.example.util.SecurityUtils;
import org.example.util.ValueUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class CoreLoginService {

  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final JwtTokenProvider jwtTokenProvider;
  private final RedisUtils redisUtils;
  private final MemberRepository memberRepository;

  /**
   * 로그인
   */
  public LoginResponse login(Role role, LoginRequest request) {
    UsernamePasswordAuthenticationToken authenticationToken =
      new UsernamePasswordAuthenticationToken(request.email(), request.password());
    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    String memberRole = authentication.getAuthorities().stream().toList().get(0).toString();
    if (role.equals(ROLE_ADMIN) && ROLE_USER.toString().equals(memberRole)) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "관리자만 접근할 수 있습니다.");
    }
    JwtToken jwtToken = jwtTokenProvider.generateToken(role, request.email());
    redisUtils.save(JwtToken.toRedisKey(request.email()), jwtToken.refreshToken(), Duration.ofDays(10));

    return LoginResponse
      .builder()
      .accessToken(jwtToken.accessToken())
      .build();
  }

  /**
   * 로그아웃
   */
  public void logout() {
    String email = SecurityUtils.getCurrentMemberEmail();
    redisUtils.delete(JwtToken.toRedisKey(email));
  }

  /**
   * Access Token 재발급
   */
  public LoginResponse refreshAccessToken(RefreshAccessTokenRequest request) {

    // Redis에서 Refresh Token 조회
    String accessTokenEmail = jwtTokenProvider.parseClaims(request.accessToken()).get(ValueUtils.EMAIL).toString();
    String refreshToken = redisUtils.getValue(JwtToken.toRedisKey(accessTokenEmail));
    if (!StringUtils.hasText(refreshToken)) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "Refresh Token이 존재하지 않습니다.");
    }
    String refreshTokenEmail = jwtTokenProvider.parseClaims(refreshToken).get(ValueUtils.EMAIL).toString();
    if (!accessTokenEmail.equals(refreshTokenEmail)) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "Refresh Token과 회원 정보가 일치하지 않습니다.");
    }

    Member member = memberRepository.findByEmail(refreshTokenEmail)
      .orElseThrow(() -> new GeneralException(ErrorStatus.NOT_FOUND, "존재하지 않는 Email 입니다."));
    JwtToken jwtToken = jwtTokenProvider.generateToken(member.getRole(), member.getEmail());
    redisUtils.save(JwtToken.toRedisKey(member.getEmail()), jwtToken.refreshToken(), Duration.ofDays(10));

    return LoginResponse
      .builder()
      .accessToken(jwtToken.accessToken())
      .build();
  }
}