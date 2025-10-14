package org.example.domain.challenge_join_log.repository;

import static org.example.domain.challenge_join_log.QChallengeJoinLog.challengeJoinLog;
import static org.example.domain.challenge_problem.QChallengeProblem.challengeProblem;
import static org.example.domain.member.QMember.member;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.challenge_join_log.controller.response.ListChallengeJoinLogDto;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ListChallengeJoinLogRepository {

  private final JPAQueryFactory queryFactory;

  public List<ListChallengeJoinLogDto> getChallengeJoinLogList() {
    return queryFactory
      .select(Projections.fields(
          ListChallengeJoinLogDto.class,
          challengeProblem.date,
          challengeJoinLog.languageType,
          member.email,
          member.name,
          challengeJoinLog.executionTime.stringValue().concat("ms").as("executionTime"),
          challengeJoinLog.memory.stringValue().concat("KB").as("memory"),
          challengeJoinLog.codeLength.stringValue().concat("B").as("codeLength")
        )
      )
      .from(challengeJoinLog)
      .innerJoin(member).on(challengeJoinLog.member.eq(member))
      .innerJoin(challengeProblem).on(challengeJoinLog.challengeProblem.eq(challengeProblem))
      .where(
        challengeProblem.date.goe(LocalDate.now().minusDays(6))
      )
      .groupBy(
        challengeProblem.date,
        challengeJoinLog.languageType,
        challengeJoinLog.id
      )
      .orderBy(
        challengeProblem.date.asc(),
        challengeJoinLog.executionTime.asc(),
        challengeJoinLog.memory.asc(),
        challengeJoinLog.codeLength.asc(),
        challengeJoinLog.submitTime.asc(),
        challengeJoinLog.languageType.asc()
      )
      .fetch();

  }
}
