package org.example.domain.study;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.config.jpa.BooleanToYNConverter;
import org.example.domain.generation.Generation;
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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "generation_id")
  private Generation generation;

  @Comment("스터디 대표 이미지 URL")
  private String profileUrl;

  @Column(nullable = false)
  @Comment("스터디 이름")
  private String name;

  @Comment("내용(에디터)")
  @Column(length = 1000000)
  private String content;

  @Enumerated(value = EnumType.STRING)
  @Column(nullable = false)
  @Comment("스터디 유형")
  private StudyType type;

  @Convert(converter = BooleanToYNConverter.class)
  @Column(nullable = false, columnDefinition = "char(1) default 'N'")
  @Comment("스터디 종료 여부")
  private Boolean endYN;

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
  public Study(String profileUrl, String name, String content, StudyType type, Generation generation) {
    this.profileUrl = profileUrl;
    this.name = name;
    this.content = content;
    this.type = type;
    this.generation = generation;
  }

  public void update(String profileUrl, String name, String content) {
    this.name = name;
    this.profileUrl = profileUrl;
    this.content = content;
  }

  public void renewGeneration(Generation generation) {
    this.generation = generation;
  }

  public void markOldGeneration(Generation oldGeneration) {
    this.name = oldGeneration.getValue() + "기 " + this.name;
    this.endYN = true;
  }

  public void end() {
    this.endYN = true;
  }

  public void end() {
    this.endYN = true;
  }
}
