package org.example.domain.challenge_join_log;

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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.config.jpa.BooleanToYNConverter;
import org.example.domain.challenge_join_log.enums.LanguageType;
import org.example.domain.challenge_problem.ChallengeProblem;
import org.example.domain.member.Member;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class ChallengeJoinLog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "date")
  private ChallengeProblem challengeProblem;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @Comment("실행 시간")
  private Integer executionTime;

  @Comment("메모리")
  private Integer memory;

  @Comment("코드 길이")
  private Integer codeLength;

  @Comment("언어")
  private LanguageType languageType;

  @Convert(converter = BooleanToYNConverter.class)
  @Column(nullable = false, columnDefinition = "char(1) default 'N'")
  @Comment("보상 변환 여부")
  private Boolean convertYn;

  @LastModifiedDate
  private LocalDateTime updatedTime;

  @Builder
  public ChallengeJoinLog(ChallengeProblem challengeProblem, Member member,
    Integer executionTime, Integer memory, Integer codeLength, LanguageType languageType) {
    this.challengeProblem = challengeProblem;
    this.member = member;
    this.executionTime = executionTime;
    this.memory = memory;
    this.codeLength = codeLength;
    this.languageType = languageType;
  }
}