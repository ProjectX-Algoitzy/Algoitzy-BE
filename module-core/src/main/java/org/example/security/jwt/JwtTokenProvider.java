package org.example.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.util.DateUtils;
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

  public JwtToken generateToken(Authentication authentication, String email) {
    String authorities = authentication.getAuthorities().stream()
      .map(GrantedAuthority::getAuthority)
      .collect(Collectors.joining(","));

      String accessToken = Jwts.builder()
      .setSubject(authentication.getName())
      .claim("auth", authorities)
      .claim("email", email)
      .setExpiration(DateUtils.ONE_HOUR)
      .signWith(key)
      .compact();

    String refreshToken = Jwts.builder()
      .setExpiration(new Date(DateUtils.ONE_DAY.getTime() * 10))
      .claim("email", email)
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
    } catch (SignatureException | SecurityException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
      log.error("유효하지 않은 토큰입니다 : {}", e.getMessage());
      throw new GeneralException(ErrorStatus.BAD_REQUEST, e.getMessage());
    } catch (ExpiredJwtException e) {
      throw new GeneralException(ErrorStatus.TOKEN_EXPIRED, "만료된 Access Token입니다.");
    }
  }

  private Claims parseClaims(String token) {
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
