package org.example.domain.problem;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.config.jpa.StringListToByteConverter;
import org.example.config.jpa.StringListToStringConverter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Problem {

  @Id
  @Column(updatable = false, nullable = false)
  @Comment("백준 문제 번호")
  private Integer number;

  @Column(nullable = false)
  @Comment("문제 이름")
  private String name;

  @Enumerated(value = EnumType.STRING)
  @Column(nullable = false)
  @Comment("난이도")
  private Level level;

  @Convert(converter = StringListToByteConverter.class)
  @Comment("지원하는 언어 목록")
  private List<String> languageList;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdTime;

  @LastModifiedDate
  private LocalDateTime updatedTime;

  @CreatedBy
  @Column(updatable = false)
  private String createdBy;

  @LastModifiedBy
  private String updatedBy;


  @Builder
  public Problem(Integer number, String name, Level level, List<String> languageList) {
    this.number = number;
    this.name = name;
    this.level = level;
    this.languageList = languageList;
  }
}
