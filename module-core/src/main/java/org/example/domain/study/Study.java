package org.example.domain.study;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.study.enums.StudyType;
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
public class Study {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Comment("스터디 대표 이미지 URL")
  private String profileUrl;

  @Column(nullable = false)
  @Comment("스터디 이름")
  private String name;

  @Column(nullable = false)
  @Comment("스터디 기수")
  private Integer generation;

  @Comment("내용(에디터)")
  @Column(length = 1000000)
  private String content;

  @Enumerated(value = EnumType.STRING)
  @Column(nullable = false)
  @Comment("스터디 유형")
  private StudyType type;

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
  public Study(String profileUrl, String name, String content, StudyType type, Integer generation) {
    this.profileUrl = profileUrl;
    this.name = name;
    this.content = content;
    this.type = type;
    this.generation = generation;
  }
}
