package org.example.domain.challenge_reward_log;

import jakarta.persistence.Column;
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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.attendance.Attendance;
import org.example.domain.attendance.enums.AttendanceType;
import org.example.domain.challenge_reward.ChallengeReward;
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
  @JoinColumn(name = "challenge_reward_id")
  private ChallengeReward challengeReward;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "attendance_id")
  private Attendance attendance;

  @Enumerated(value = EnumType.STRING)
  @Comment("갱신 출석부 유형")
  private AttendanceType attendanceType;

  @Comment("갱신 후 보상 개수")
  private Long rewardCount;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdTime;

  @CreatedBy
  @Column(updatable = false)
  private String createdBy;

  @Builder
  public ChallengeRewardLog(ChallengeReward challengeReward, Attendance attendance, Long rewardCount) {
    this.challengeReward = challengeReward;
    this.attendance = attendance;
    this.rewardCount = rewardCount;
  }
}