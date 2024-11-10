package org.example.domain.study.repository;

import static org.example.domain.generation.QGeneration.generation;
import static org.example.domain.study.QStudy.study;
import static org.example.domain.study_member.QStudyMember.studyMember;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.study.controller.response.ListStudyDto;
import org.example.domain.study.enums.StudyType;
import org.example.domain.study_member.enums.StudyMemberRole;
import org.example.domain.study_member.enums.StudyMemberStatus;
import org.example.util.SecurityUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ListStudyRepository {

  private final JPAQueryFactory queryFactory;

  /**
   * 스터디 목록 조회
   */
  public List<ListStudyDto> getStudyList(StudyType studyType) {
    Integer maxGeneration = queryFactory
      .select(generation.value.max())
      .from(generation)
      .fetchOne();

    return queryFactory
      .select(Projections.fields(ListStudyDto.class,
          study.id.as("studyId"),
          study.profileUrl,
          Expressions.as(
            JPAExpressions
              .select(studyMember.count())
              .from(studyMember)
              .where(
                studyMember.study.eq(study),
                studyMember.status.eq(StudyMemberStatus.PASS)
              )
            , "memberCount"),
          study.name.as("studyName"),
          study.type.stringValue().as("studyType"),
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
      .leftJoin(studyMember).on(studyMember.study.eq(study))
      .innerJoin(generation).on(study.generation.eq(generation))
      .where(
        isMaxGeneration(studyType, maxGeneration),
        filterByStudyType(studyType),
        study.endYN.isFalse()
      )
      .groupBy(study)
      .orderBy(study.createdTime.desc())
      .fetch();
  }

  private static BooleanExpression filterByStudyType(StudyType studyType) {
    return (studyType == null) ?
      studyMember.member.email.eq(SecurityUtils.getCurrentMemberEmail())
        .and(studyMember.status.eq(StudyMemberStatus.PASS)) // 나의 스터디
      : study.type.eq(studyType);
  }

  private static BooleanExpression isMaxGeneration(StudyType studyType, Integer maxGeneration) {
    if (studyType != null && studyType.equals(StudyType.TEMP)) {
      return null;
    }
    return generation.value.eq(maxGeneration);
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

  /**
   * 마이페이지 스터디 정보
   */
  public List<ListStudyDto> getMyPageStudy(Long memberId, boolean passYN) {
    return queryFactory
      .select(Projections.fields(
          ListStudyDto.class,
          study.id.as("studyId"),
          study.profileUrl,
          Expressions.as(
            JPAExpressions
              .select(studyMember.count())
              .from(studyMember)
              .where(
                studyMember.study.eq(study),
                studyMember.status.eq(StudyMemberStatus.PASS)
              )
            , "memberCount"),
          study.name.as("studyName"),
          study.type.stringValue().as("studyType"),
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
      .innerJoin(studyMember).on(studyMember.study.eq(study))
      .where(
        getPassStudy(memberId, passYN),
        (passYN) ? null : study.endYN.isFalse()
      )
      .groupBy(study)
      .orderBy(study.id.desc())
      .fetch();
  }

  private BooleanExpression getPassStudy(Long memberId, boolean passYN) {
    if (passYN) return studyMember.member.id.eq(memberId).and(studyMember.status.eq(StudyMemberStatus.PASS));
    else return studyMember.member.id.eq(memberId)
      .and(studyMember.status.ne(StudyMemberStatus.PASS))
      .and(studyMember.status.ne(StudyMemberStatus.DOCUMENT_FAIL))
      .and(studyMember.status.ne(StudyMemberStatus.FAIL));
  }
}
