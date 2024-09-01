package org.example.security;

import lombok.RequiredArgsConstructor;
import org.example.security.jwt.JwtAuthenticationFilter;
import org.example.security.jwt.JwtExceptionFilter;
import org.example.security.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtTokenProvider jwtTokenProvider;
  private final JwtExceptionFilter jwtExceptionFilter;

  @Bean
  public PasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
      .authorizeHttpRequests(
        auth ->
          auth
            .requestMatchers(HttpMethod.OPTIONS).permitAll()
            .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**")).permitAll()
            .requestMatchers(new AntPathRequestMatcher("/**/swagger-ui/**")).permitAll()
            .requestMatchers(new AntPathRequestMatcher("/member/login/**")).permitAll()
            .requestMatchers(new AntPathRequestMatcher("/member/refresh-token")).permitAll()
            .requestMatchers(new AntPathRequestMatcher("/member/find-email")).permitAll()
            .requestMatchers(new AntPathRequestMatcher("/sign-up/**")).permitAll()
            .requestMatchers(new AntPathRequestMatcher("/email/**")).permitAll()
            .requestMatchers(new AntPathRequestMatcher("/sms/**")).permitAll()
            .requestMatchers(new AntPathRequestMatcher("/health/**")).permitAll()
            // 랜딩 페이지
            .requestMatchers(new AntPathRequestMatcher("/study/count")).permitAll()
            .requestMatchers(new AntPathRequestMatcher("/generation/max")).permitAll()

            .requestMatchers(new AntPathRequestMatcher("/s3/**")).permitAll()
            .anyRequest().authenticated()
      )
      .csrf(AbstractHttpConfigurer::disable)
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
      .addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class)
      .build();
  }
}
