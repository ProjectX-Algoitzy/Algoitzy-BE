package org.example.domain.challenge_join_log.repository;

import static org.example.domain.challenge_join_log.QChallengeJoinLog.challengeJoinLog;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.example.domain.challenge_join_log.ChallengeJoinLog;
import org.example.domain.challenge_join_log.enums.LanguageType;
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
}
