package org.example.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.example.api_response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
    try {
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
