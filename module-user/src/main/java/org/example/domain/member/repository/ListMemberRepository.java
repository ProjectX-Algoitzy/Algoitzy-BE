package org.example.domain.member.repository;

import static org.example.domain.generation.QGeneration.generation;
import static org.example.domain.study_member.QStudyMember.studyMember;
import static org.example.domain.week.QWeek.week;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.member.Member;
import org.example.domain.study_member.enums.StudyMemberStatus;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ListMemberRepository {

  private final JPAQueryFactory queryFactory;

  /**
   * 데일리 챌린지 회원 조회
   */
  public List<Member> getChallengeMemberList() {
    Integer maxGeneration = queryFactory
      .select(generation.value.max())
      .from(generation)
      .fetchOne();

    boolean studyStartYn = queryFactory
      .selectFrom(week)
      .where(
        week.generation.value.eq(maxGeneration),
        week.startTime.before(LocalDateTime.now())
      )
      .fetchFirst() != null;

    return queryFactory
      .select(studyMember.member)
      .from(studyMember)
      .where(
        studyMember.study.generation.value.eq(studyStartYn ? maxGeneration : maxGeneration - 1),
        studyMember.status.eq(StudyMemberStatus.PASS)
      )
      .fetch();
  }


}
