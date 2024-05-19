package org.example.domain.study.repository;

import static org.example.domain.study.QStudy.study;
import static org.example.domain.study_member.QStudyMember.studyMember;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.domain.study.controller.response.DetailTempStudyResponse;
import org.example.domain.study_member.enums.StudyMemberRole;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DetailStudyRepository {

  private final JPAQueryFactory queryFactory;

  /**
   * 자율 스터디 상세 조회
   */
  public DetailTempStudyResponse getTempStudy(Long studyId) {
    return queryFactory
      .select(Projections.fields(DetailTempStudyResponse.class,
          study.imageUrl,
          Expressions.as(
            JPAExpressions
              .select(studyMember.count())
              .from(studyMember)
              .where(
                studyMember.study.eq(study)
              )
            , "memberCount"),
          study.name.as("studyName"),
          study.memberLimit,
          Expressions.as(
            JPAExpressions
              .select(studyMember.member.name)
              .from(studyMember)
              .where(
                studyMember.study.eq(study),
                studyMember.role.eq(StudyMemberRole.LEADER)
              )
            , "leaderName"),
          study.createdTime,
          study.subject,
          study.target,
          Expressions.as(
            JPAExpressions
              .select(studyMember.role.stringValue())
              .from(studyMember)
              .where(
                studyMember.study.eq(study),
                studyMember.role.eq(StudyMemberRole.LEADER)
              )
            , "memberRole")
        )
      )
      .from(study)
      .where(
        study.id.eq(studyId)
      )
      .fetchOne();
  }
}
