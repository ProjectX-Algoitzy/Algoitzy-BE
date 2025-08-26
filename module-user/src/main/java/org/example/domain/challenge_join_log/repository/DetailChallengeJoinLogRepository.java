package org.example.domain.challenge_join_log.repository;

import static org.example.domain.challenge_join_log.QChallengeJoinLog.challengeJoinLog;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.example.domain.challenge_join_log.ChallengeJoinLog;
import org.example.domain.challenge_join_log.enums.LanguageType;
import org.example.domain.member.Member;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DetailChallengeJoinLogRepository {

  private final JPAQueryFactory queryFactory;

  public ChallengeJoinLog getRewardTarget(LanguageType cpp) {
    return queryFactory
      .selectFrom(challengeJoinLog)
      .where(
        challengeJoinLog.challengeProblem.date.eq(LocalDate.now().minusDays(1)),
        challengeJoinLog.languageType.eq(cpp)
      )
      .orderBy(
        challengeJoinLog.executionTime.asc(),
        challengeJoinLog.memory.asc(),
        challengeJoinLog.codeLength.asc(),
        challengeJoinLog.submitTime.asc()
      )
      .limit(1)
      .fetchOne();
  }

  public boolean isJoinedMember(Member member) {
    LocalDate now = LocalDate.now();
    return queryFactory
      .selectFrom(challengeJoinLog)
      .where(
        challengeJoinLog.createdTime.between(now.atStartOfDay(), now.atTime(LocalTime.MAX)),
        challengeJoinLog.member.eq(member)
      )
      .fetchOne() != null;
  }
}
