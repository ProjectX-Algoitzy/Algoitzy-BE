package org.example.domain.curriculum.repository;

import static org.example.domain.curriculum.QCurriculum.curriculum;
import static org.example.domain.member.QMember.member;
import static org.example.domain.study.QStudy.study;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.curriculum.controller.response.ListCurriculumDto;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ListCurriculumRepository {

  private final JPAQueryFactory queryFactory;

  /**
   * 커리큘럼 목록 조회
   */
  public List<ListCurriculumDto> getCurriculumList() {
    return queryFactory
      .select(Projections.fields(ListCurriculumDto.class,
        curriculum.id.as("curriculumId"),
        curriculum.title,
        Expressions.as(
          JPAExpressions
            .select(study.name)
            .from(study)
            .where(study.eq(curriculum.study)
            )
          , "studyName"),
        Expressions.as(
          JPAExpressions
            .select(member.name)
            .from(member)
            .where(member.email.eq(curriculum.createdBy)
            )
          , "createdName"),
        curriculum.createdTime,
        Expressions.as(
          JPAExpressions
            .select(member.name)
            .from(member)
            .where(member.email.eq(curriculum.updatedBy)
            )
          , "updatedName"),
        curriculum.updatedTime
        )
      )
      .from(curriculum)
      .orderBy(curriculum.week.asc())
      .fetch();
  }
}
