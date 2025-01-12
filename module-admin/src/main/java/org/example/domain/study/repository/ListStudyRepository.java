package org.example.domain.study.repository;

import static org.example.domain.generation.QGeneration.generation;
import static org.example.domain.study.QStudy.study;
import static org.example.domain.study_member.QStudyMember.studyMember;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.example.domain.study.Study;
import org.example.domain.study.controller.response.ListRegularStudyDto;
import org.example.domain.study.enums.StudyType;
import org.example.domain.study_member.enums.StudyMemberStatus;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ListStudyRepository {

  private final JPAQueryFactory queryFactory;

  /**
   * 정규 스터디 목록 조회
   */
  public List<ListRegularStudyDto> getRegularStudyList(boolean currentGeneration) {
    Integer maxGeneration = queryFactory
      .select(generation.value.max())
      .from(generation)
      .fetchOne();

    return queryFactory
      .select(Projections.fields(ListRegularStudyDto.class,
          study.id.as("studyId"),
          study.profileUrl,
          study.name,
          Expressions.as(
            JPAExpressions
              .select(studyMember.count())
              .from(studyMember)
              .where(
                studyMember.study.eq(study),
                studyMember.status.eq(StudyMemberStatus.PASS)
              )
            , "memberCount")
        )
      )
      .from(study)
      .where(
        study.type.eq(StudyType.REGULAR),
        study.generation.value.goe(currentGeneration ? maxGeneration : Integer.valueOf(16)) // 서비스 적용 기수 : 16기
      )
      .orderBy(study.generation.value.desc())
      .fetch();
  }

  public List<Study> getOldGenerationStudyList(StudyType type) {
    Integer oldGeneration =
      Objects.requireNonNull(
        queryFactory
          .select(generation.value.max())
          .from(generation)
          .fetchOne()) - 1;

    return queryFactory
      .selectFrom(study)
      .where(
        study.type.eq(type),
        study.generation.value.eq(oldGeneration)
      )
      .fetch();
  }
}
