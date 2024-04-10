package org.example.config.jpa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class JpaAuditConfig {
  @Bean
  public AuditorAware<String> auditorProvider() {
    return new AuditAwareImpl();
  }
}
