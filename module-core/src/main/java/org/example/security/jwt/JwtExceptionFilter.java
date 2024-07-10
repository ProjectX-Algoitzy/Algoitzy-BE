package org.example.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.example.api_response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
public class JwtExceptionFilter extends OncePerRequestFilter {

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    String[] excludePath = {
      "/v3/api-docs",
      "/swagger-ui",
      "/sign-up",
      "/member/login",
      "/member/find-email",
      "/email",
      "/sms",
      "/health",
      "/study/count",
      "/generation/max",
      "/s3"
    };
    String path = request.getRequestURI();
    return Arrays.stream(excludePath).anyMatch(path::startsWith);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
    try {
      log.info("JwtExceptionFilter Request URI : {}", request.getRequestURI());
      chain.doFilter(request, response);
    } catch (JwtException ex) {
      try {
        setErrorResponse(HttpStatus.UNAUTHORIZED, response, ex);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    } catch (ServletException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable e) throws IOException {
    String json = new ObjectMapper()
      .writeValueAsString(
        ApiResponse.builder()
          .isSuccess(false)
          .code("40000")
          .message(e.getMessage())
          .build()
      );

    response.setStatus(status.value());
    response.setContentType("application/json; charset=UTF-8");
    response.getWriter().write(json);
  }
}
