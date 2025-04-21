package org.example.domain.study_member.repository;

import static org.example.domain.generation.QGeneration.generation;
import static org.example.domain.study.QStudy.study;
import static org.example.domain.study_member.QStudyMember.studyMember;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.domain.member.Member;
import org.example.domain.member.QMember;
import org.example.domain.study.enums.StudyType;
import org.example.domain.study_member.StudyMember;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DetailStudyMemberRepository {

  private final JPAQueryFactory queryFactory;


  public StudyMember getStudyMember(Member member) {
    Integer maxGeneration = queryFactory
      .select(generation.value.max())
      .from(generation)
      .fetchOne();

    return queryFactory
      .selectFrom(studyMember)
      .where(
        study.type.eq(StudyType.REGULAR),
        study.generation.value.eq(maxGeneration),
        studyMember.member.eq(member)
      )
      .innerJoin(study).on(studyMember.study.eq(study))
      .innerJoin(QMember.member).on(studyMember.member.eq(member))
      .innerJoin(generation).on(study.generation.eq(generation))
      .fetchOne();
  }
}
