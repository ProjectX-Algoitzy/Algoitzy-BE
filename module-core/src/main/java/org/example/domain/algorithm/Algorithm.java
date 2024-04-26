package org.example.domain.algorithm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
public class Algorithm {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  @Comment("유형 이름")
  private String name;

  @Comment("설명")
  private String description;

  @Comment("이론 YouTube Url")
  private String theoryUrl;

  @Comment("GitBook Url")
  private String gitbookUrl;

  @Comment("C++ 강의 Url")
  private String cppUrl;

  @Comment("Python 강의 Url")
  private String pythonUrl;

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
  public Algorithm(String name, String description, String theoryUrl, String gitbookUrl, String cppUrl, String pythonUrl) {
    this.name = name;
    this.description = description;
    this.theoryUrl = theoryUrl;
    this.gitbookUrl = gitbookUrl;
    this.cppUrl = cppUrl;
    this.pythonUrl = pythonUrl;
  }
}
