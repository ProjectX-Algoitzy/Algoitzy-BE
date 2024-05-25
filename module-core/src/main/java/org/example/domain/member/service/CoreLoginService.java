package org.example.domain.member.service;

import static org.example.domain.member.enums.Role.ROLE_ADMIN;
import static org.example.domain.member.enums.Role.ROLE_USER;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.member.controller.request.LoginRequest;
import org.example.domain.member.controller.response.LoginResponse;
import org.example.domain.member.enums.Role;
import org.example.security.jwt.JwtToken;
import org.example.security.jwt.JwtTokenProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CoreLoginService {

  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final JwtTokenProvider jwtTokenProvider;

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
    JwtToken jwtToken = jwtTokenProvider.generateToken(authentication, request.email());

    return LoginResponse
      .builder()
      .accessToken(jwtToken.accessToken())
      .build();
  }

}