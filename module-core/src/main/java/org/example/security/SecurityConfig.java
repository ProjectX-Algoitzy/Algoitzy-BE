package org.example.security;

import java.util.Arrays;
import java.util.List;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(
      Arrays.asList(
        "https://www.kau-koala.com",
        "https://admin.kau-koala.com",
        "https://user-api.kau-koala.com",
        "https://admin-api.kau-koala.com",
        "https://user-dev-front.kau-koala.com",
        "https://admin-dev-front.kau-koala.com",
        "https://user-dev.kau-koala.com",
        "https://admin-dev.kau-koala.com",
        "http://localhost:3333",
        "http://localhost:4444"
      ));
    configuration.setAllowedMethods(List.of("*"));
    configuration.setAllowedHeaders(List.of("*"));
    configuration.setAllowCredentials(true);
    configuration.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
      .cors(cors -> cors.configurationSource(corsConfigurationSource()))
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
