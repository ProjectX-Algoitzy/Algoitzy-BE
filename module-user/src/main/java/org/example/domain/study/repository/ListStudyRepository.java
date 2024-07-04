package org.example.domain.study.repository;

import static org.example.domain.generation.QGeneration.generation;
import static org.example.domain.study.QStudy.study;
import static org.example.domain.study_member.QStudyMember.studyMember;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.study.controller.response.ListStudyDto;
import org.example.domain.study.enums.StudyType;
import org.example.domain.study_member.enums.StudyMemberRole;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ListStudyRepository {

  private final JPAQueryFactory queryFactory;

  /**
   * 스터디 목록 조회
   */
  public List<ListStudyDto> getStudyList(StudyType studyType) {
    return queryFactory
      .select(Projections.fields(ListStudyDto.class,
          study.id.as("studyId"),
          study.profileUrl,
          Expressions.as(
            JPAExpressions
              .select(studyMember.count())
              .from(studyMember)
              .where(
                studyMember.study.eq(study)
              )
            , "memberCount"),
          study.name.as("studyName"),
          Expressions.as(
            JPAExpressions
              .select(studyMember.member.profileUrl)
              .from(studyMember)
              .where(
                studyMember.study.eq(study),
                studyMember.role.eq(StudyMemberRole.LEADER)
              )
            , "leaderProfileUrl"),
        Expressions.as(
            JPAExpressions
              .select(studyMember.member.name)
              .from(studyMember)
              .where(
                studyMember.study.eq(study),
                studyMember.role.eq(StudyMemberRole.LEADER)
              )
            , "leaderName"),
          study.createdTime
        )
      )
      .from(study)
      .where(
        study.type.eq(studyType)
      )
      .orderBy(study.createdTime.desc())
      .fetch();
  }

  /**
   * 최신 기수 스터디 개수
   */
  public Integer getStudyCount() {
    Integer maxGeneration = queryFactory
      .select(generation.value.max())
      .from(generation)
      .fetchOne();

    return queryFactory
      .selectFrom(study)
      .where(
        study.generation.value.eq(maxGeneration)
      )
      .fetch().size();
  }
}
