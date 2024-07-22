package org.example.domain.attendance;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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
import org.example.config.jpa.BooleanToYNConverter;
import org.example.domain.study_member.StudyMember;
import org.example.domain.week.Week;
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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "week_id")
  private Week week;

  @Convert(converter = BooleanToYNConverter.class)
  @Column(nullable = false, columnDefinition = "char(1) default 'N'")
  @Comment("문제 할당량 충족 여부")
  private Boolean problemYN;

  @Convert(converter = BooleanToYNConverter.class)
  @Column(nullable = false, columnDefinition = "char(1) default 'N'")
  @Comment("블로그 포스팅 여부")
  private Boolean blogYN;

  @Convert(converter = BooleanToYNConverter.class)
  @Column(nullable = false, columnDefinition = "char(1) default 'N'")
  @Comment("모의테스트 참여 여부")
  private Boolean workbookYN;

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
  public Attendance(StudyMember studyMember, Week week, Boolean problemYN, Boolean blogYN, Boolean workbookYN) {
    this.studyMember = studyMember;
    this.week = week;
    this.problemYN = problemYN;
    this.blogYN = blogYN;
    this.workbookYN = workbookYN;
  }

  public int getAbsentCount() {
    int absentCount = 3;
    if (this.problemYN) absentCount--;
    if (this.blogYN) absentCount--;
    if (this.workbookYN) absentCount--;
    return absentCount;
  }
}
