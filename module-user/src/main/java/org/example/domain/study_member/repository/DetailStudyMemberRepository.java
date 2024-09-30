package org.example.domain.study_member.repository;

import static org.example.domain.generation.QGeneration.generation;
import static org.example.domain.study.QStudy.study;
import static org.example.domain.study_member.QStudyMember.studyMember;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.domain.member.Member;
import org.example.domain.study.enums.StudyType;
import org.example.domain.study_member.StudyMember;
import org.example.domain.study_member.enums.StudyMemberRole;
import org.example.domain.study_member.enums.StudyMemberStatus;
import org.example.util.SecurityUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DetailStudyMemberRepository {

  private final JPAQueryFactory queryFactory;

  /**
   * 최신 기수에서 지원한 정규 스터디 조회
   */
  public Optional<StudyMember> getRegularStudy(Member member) {
    Integer maxGeneration = queryFactory
      .select(generation.value.max())
      .from(generation)
      .fetchOne();

    return Optional.ofNullable(queryFactory
      .selectFrom(studyMember)
      .innerJoin(study).on(studyMember.study.eq(study))
      .innerJoin(generation).on(study.generation.eq(generation))
      .where(
        study.generation.value.eq(maxGeneration),
        study.type.eq(StudyType.REGULAR),
        studyMember.member.eq(member)
      )
      .fetchOne());
  }

  public boolean isRegularStudyMember() {
    return !queryFactory
      .selectFrom(studyMember)
      .innerJoin(study).on(studyMember.study.eq(study))
      .where(
        study.type.eq(StudyType.REGULAR),
        studyMember.member.email.eq(SecurityUtils.getCurrentMemberEmail()),
        studyMember.status.eq(StudyMemberStatus.PASS)
      )
      .fetch()
      .isEmpty();
  }

  public StudyMember getTempStudyLeader(Long studyId) {
    return queryFactory
      .selectFrom(studyMember)
      .where(
        study.type.eq(StudyType.TEMP),
        studyMember.role.eq(StudyMemberRole.LEADER),
        study.id.eq(studyId)
      )
      .fetchOne();
  }
}
