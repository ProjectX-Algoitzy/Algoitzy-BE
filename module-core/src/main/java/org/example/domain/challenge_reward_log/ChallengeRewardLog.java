package org.example.domain.challenge_reward_log;

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
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.config.jpa.IntegerListToStringConverter;
import org.example.domain.attendance.Attendance;
import org.example.domain.attendance.enums.AttendanceType;
import org.example.domain.member.Member;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class ChallengeRewardLog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "attendance_id")
  private Attendance attendance;

  @Enumerated(value = EnumType.STRING)
  @Comment("갱신 출석부 유형")
  private AttendanceType attendanceType;

  @Convert(converter = IntegerListToStringConverter.class)
  @Comment("1등한 문제 목록")
  private List<Integer> problemList;

  @Comment("갱신 후 보상 개수")
  private Long rewardCount;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdTime;

  @CreatedBy
  @Column(updatable = false)
  private String createdBy;

  @Builder
  public ChallengeRewardLog(Member member, Attendance attendance, List<Integer> problemList, Long rewardCount) {
    this.member = member;
    this.attendance = attendance;
    this.problemList = problemList;
    this.rewardCount = rewardCount;
  }
}