package org.example.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.member.enums.Role;
import org.example.util.ValueUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtTokenProvider {

  private final Key key;

  public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    this.key = Keys.hmacShaKeyFor(keyBytes);
  }

  public JwtToken generateToken(Role role, String email) {

    Date issuedAt = Date.from(Instant.now().truncatedTo(ChronoUnit.SECONDS));

    LocalDateTime now = LocalDateTime.now();
    ZonedDateTime expireZone = now.plus(1, ChronoUnit.HOURS).atZone(ZoneId.systemDefault());

    String accessToken = Jwts.builder()
      .setSubject(email)
      .claim("auth", role)
      .claim("email", email)
      .setIssuedAt(issuedAt)
      .setExpiration(Date.from(expireZone.toInstant()))
      .signWith(key)
      .compact();

    expireZone = now.plus(10, ChronoUnit.DAYS).atZone(ZoneId.systemDefault());
    String refreshToken = Jwts.builder()
      .setSubject(email)
      .claim("email", email)
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(Date.from(expireZone.toInstant()))
      .signWith(key)
      .compact();

    return JwtToken.builder()
      .grantType("Bearer")
      .accessToken(accessToken)
      .refreshToken(refreshToken)
      .build();
  }

  public Authentication getAuthentication(String accessToken) {

    Claims claims = parseClaims(accessToken);
    if (claims.get("auth") == null) {
      throw new GeneralException(ErrorStatus.VALIDATION_ERROR, "권한 정보가 없는 token 입니다.");
    }

    Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(","))
      .map(SimpleGrantedAuthority::new)
      .collect(Collectors.toList());

    UserDetails principal = new User(claims.getSubject(), "", authorities);
    return new UsernamePasswordAuthenticationToken(principal, "", authorities);
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token);
      return true;
    } catch (SignatureException e) {
      log.error("사용자 인증에 실패했습니다. : {}", e.getMessage());
      throw new JwtException("사용자 인증에 실패했습니다.");
    } catch (ExpiredJwtException e) {
      log.error("{} : {}",ValueUtils.TOKEN_EXPIRED_MESSAGE, e.getMessage());
      throw new JwtException(ValueUtils.TOKEN_EXPIRED_MESSAGE);
    } catch (SecurityException e) {
      log.error("허가되지 않는 사용자입니다. : {}", e.getMessage());
      throw new JwtException("허가되지 않는 사용자입니다.");
    } catch (MalformedJwtException e) {
      log.error("올바르지 않은 토큰 형식입니다. : {}", e.getMessage());
      throw new JwtException("올바르지 않은 토큰 형식입니다.");
    } catch (UnsupportedJwtException e) {
      log.error("지원되지 않는 토큰입니다. : {}", e.getMessage());
      throw new JwtException("지원되지 않는 토큰입니다.");
    } catch (IllegalArgumentException e) {
      log.error("올바르지 않은 토큰 값입니다. : {}", e.getMessage());
      throw new JwtException("올바르지 않은 토큰 값입니다.");
    }
  }

  public Claims parseClaims(String token) {
    try {
      return Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody();
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }
  }

}
