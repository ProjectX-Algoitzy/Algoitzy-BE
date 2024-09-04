package org.example.domain.study.repository;

import static org.example.domain.answer.QAnswer.answer;
import static org.example.domain.application.QApplication.application;
import static org.example.domain.study.QStudy.study;
import static org.example.domain.study_member.QStudyMember.studyMember;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.domain.study.controller.response.DetailTempStudyResponse;
import org.example.domain.study.controller.response.RegularStudyInfoResponse;
import org.example.domain.study_member.enums.StudyMemberRole;
import org.example.domain.study_member.enums.StudyMemberStatus;
import org.example.util.SecurityUtils;
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
          study.content,
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
          study.createdTime,
          Expressions.as(
            JPAExpressions
              .select(studyMember.role.stringValue())
              .from(studyMember)
              .where(
                studyMember.study.eq(study),
                studyMember.member.email.eq(SecurityUtils.getCurrentMemberEmail())
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

  /**
   * 정규 스터디 정보 조회
   */
  public RegularStudyInfoResponse getRegularStudyInfo(Long studyId) {
    return queryFactory
      .select(Projections.fields(RegularStudyInfoResponse.class,
          study.profileUrl,
          study.name.as("studyName"),
          Expressions.as(
            JPAExpressions
              .select(studyMember.count())
              .from(studyMember)
              .where(
                studyMember.study.eq(study),
                studyMember.status.eq(StudyMemberStatus.PASS)
              )
            , "memberCount"),
          Expressions.as(
            JPAExpressions
              .select(application.id)
              .from(application)
              .where(
                application.study.eq(study),
                application.confirmYN.isTrue()
              )
            , "applicationId"),
          Expressions.as(
            JPAExpressions
              .select(answer.id)
              .from(answer)
              .where(
                answer.application.study.eq(study),
                answer.createdBy.eq(SecurityUtils.getCurrentMemberEmail())
              )
            , "answerId"),
          Expressions.as(
            JPAExpressions
              .select()
              .from(answer)
              .where(
                answer.application.study.eq(study),
                answer.createdBy.eq(SecurityUtils.getCurrentMemberEmail())
              )
              .exists()
            , "answerYN"),
          study.createdTime,
          Expressions.as(
            JPAExpressions
              .select(studyMember.role.stringValue())
              .from(studyMember)
              .where(
                studyMember.study.eq(study),
                studyMember.member.email.eq(SecurityUtils.getCurrentMemberEmail())
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
