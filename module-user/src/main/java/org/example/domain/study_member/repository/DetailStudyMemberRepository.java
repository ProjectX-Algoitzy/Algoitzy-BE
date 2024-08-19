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
}
