package org.example.domain.challenge_join_log.repository;

import static org.example.domain.challenge_join_log.QChallengeJoinLog.challengeJoinLog;
import static org.example.domain.challenge_problem.QChallengeProblem.challengeProblem;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
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

  public boolean isJoinedMember(Member member, LocalDate date) {
    return !queryFactory
      .selectFrom(challengeJoinLog)
      .innerJoin(challengeProblem).on(challengeJoinLog.challengeProblem.eq(challengeProblem))
      .where(
        challengeProblem.date.eq(date),
        challengeJoinLog.member.eq(member)
      )
      .fetch().isEmpty();
  }
}
