package org.example.domain.study;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.rule.Rule;
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

  @OneToMany(mappedBy = "study", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Rule> ruleList;

  @Comment("스터디 대표 이미지 URL")
  private String profileUrl;

  @Column(nullable = false)
  @Comment("스터디 이름")
  private String name;

  @Comment("진행 방식")
  private String way;

  @Comment("주제")
  private String subject;

  @Comment("모집 인원")
  private Integer memberLimit;

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

  @Column(nullable = false)
  @Comment("모집 대상")
  private String target;

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
  public Study(String profileUrl, String name, String content, String way, String subject,
    StudyType type, String target, Integer memberLimit, List<Rule> ruleList,
    Integer generation) {
    this.profileUrl = profileUrl;
    this.name = name;
    this.content = content;
    this.way = way;
    this.subject = subject;
    this.type = type;
    this.target = target;
    this.memberLimit = memberLimit;
    this.ruleList = ruleList;
    this.generation = generation;
  }
}
