package org.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
      .allowedOrigins(
        // production front
        "https://kau-koala.com",
        "https://admin.kau-koala.com",
        // production back
        "https://api.kau-koala.com",
        "https://admin-api.kau-koala.com",
        // dev front
        "https://user-dev-front.kau-koala.com",
        "https://admin-dev-front.kau-koala.com",
        // dev back
        "https://user-dev.kau-koala.com",
        "https://admin-dev.kau-koala.com",
        // local
        "http://localhost:3000"
      )
      .allowedMethods("*")
      .maxAge(3600);
  }
}

