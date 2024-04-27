package org.example.domain.attendance;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
import org.example.domain.study_member.StudyMember;
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
public class Attendance {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "study_member_id")
  private StudyMember studyMember;

  @Column(nullable = false)
  @Comment("주차")
  private Integer week;

  @Column(nullable = false)
  @Comment("문제 할당량 충족 여부")
  private Boolean solvedProblem;

  @Column(nullable = false)
  @Comment("블로그 포스팅 여부")
  private Boolean postedBlog;

  @Column(nullable = false)
  @Comment("모의테스트 참여 여부")
  private Boolean tookTest;

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
  public Attendance(StudyMember studyMember, Integer week, Boolean solvedProblem, Boolean postedBlog, Boolean tookTest) {
    this.studyMember = studyMember;
    this.week = week;
    this.solvedProblem = solvedProblem;
    this.postedBlog = postedBlog;
    this.tookTest = tookTest;
  }
}
