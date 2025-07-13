package org.example.domain.challenge_reward_log.repository;

import static org.example.domain.attendance.QAttendance.attendance;
import static org.example.domain.challenge_reward_log.QChallengeRewardLog.challengeRewardLog;
import static org.example.domain.generation.QGeneration.generation;
import static org.example.domain.member.QMember.member;
import static org.example.domain.study.QStudy.study;
import static org.example.domain.study_member.QStudyMember.studyMember;
import static org.example.domain.week.QWeek.week;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.challenge_reward_log.controller.request.SearchChallengeRewardLogRequest;
import org.example.domain.challenge_reward_log.controller.response.ListChallengeRewardLogDto;
import org.example.domain.challenge_reward_log.enums.ChallengeRewardLogType;
import org.example.util.SecurityUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ListChallengeRewardLogRepository {

  private final JPAQueryFactory queryFactory;

  /**
   * 내 챌린지 보상 이력 조회
   */
  public List<ListChallengeRewardLogDto> getChallengeRewardLogList(SearchChallengeRewardLogRequest request) {
    return queryFactory
      .select(
        Projections.fields(
          ListChallengeRewardLogDto.class,
          challengeRewardLog.logType.stringValue().as("logType"),
          challengeRewardLog.problemList,
          study.name.as("studyName"),
          generation.value.as("generation"),
          week.value.as("week"),
          challengeRewardLog.attendanceType,
          challengeRewardLog.createdTime.as("logDate"),
          challengeRewardLog.rewardCount
        )
      )
      .from(challengeRewardLog)
      .innerJoin(member).on(challengeRewardLog.member.eq(member))
      .leftJoin(attendance).on(challengeRewardLog.attendance.eq(attendance))
      .leftJoin(studyMember).on(attendance.studyMember.eq(studyMember))
      .leftJoin(study).on(studyMember.study.eq(study))
      .leftJoin(generation).on(study.generation.eq(generation))
      .leftJoin(week).on(attendance.week.eq(week))
      .where(
        member.email.eq(SecurityUtils.getCurrentMemberEmail()),
        logTypeEq(request.logType())
      )
      .orderBy(challengeRewardLog.createdTime.desc())
      .fetch();
  }

  private BooleanExpression logTypeEq(ChallengeRewardLogType logType) {
    if (logType == null) {
      return null;
    }
    return challengeRewardLog.logType.eq(logType);
  }
}
