package org.example.domain.api_request;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.api_request.enums.ApiModule;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class ApiRequest {

  @Id
  @Enumerated(value = EnumType.STRING)
  @Comment("요청받은 모듈")
  private ApiModule module;

  @LastModifiedDate
  private LocalDateTime lastRequestTime;

  @Builder
  public ApiRequest(ApiModule module) {
    this.module = module;
  }
}
