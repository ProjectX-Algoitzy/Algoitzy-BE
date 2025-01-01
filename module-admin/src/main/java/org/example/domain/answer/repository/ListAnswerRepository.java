package org.example.domain.answer.repository;


import static org.example.domain.answer.QAnswer.answer;
import static org.example.domain.application.QApplication.application;
import static org.example.domain.interview.QInterview.interview;
import static org.example.domain.member.QMember.member;
import static org.example.domain.study.QStudy.study;
import static org.example.domain.study_member.QStudyMember.studyMember;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.answer.controller.request.SearchAnswerRequest;
import org.example.domain.answer.controller.response.ListAnswerDto;
import org.example.domain.study_member.enums.StudyMemberStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ListAnswerRepository {

  private final JPAQueryFactory queryFactory;

  /**
   * 작성한 지원서 목록 조회
   */
  public Page<ListAnswerDto> getAnswerList(SearchAnswerRequest request) {
    List<ListAnswerDto> answerList = queryFactory
      .select(Projections.fields(ListAnswerDto.class,
          answer.id.as("answerId"),
          study.name.as("studyName"),
          member.grade,
          member.major,
          member.name.as("submitName"),
          member.email.as("submitEmail"),
          studyMember.status.stringValue().as("status"),
          answer.updatedTime.as("submitTime"),
          interview.id.as("interviewId"),
          interview.time.as("interviewTime")
        )
      )
      .from(answer)
      .innerJoin(application).on(answer.application.eq(application))
      .innerJoin(study).on(application.study.eq(study))
      .innerJoin(member).on(answer.createdBy.eq(member.email))
      .innerJoin(studyMember).on(member.eq(studyMember.member), study.eq(studyMember.study))
      .innerJoin(interview).on(studyMember.eq(interview.studyMember))
      .where(
        study.generation.value.eq(request.generation()),
        statusEq(request.status()),
        answer.confirmYN.isTrue()
      )
      .offset(request.pageRequest().getOffset())
      .limit(request.pageRequest().getPageSize())
      .fetch();

    JPAQuery<Long> countQuery = queryFactory
      .select(answer.count())
      .from(answer)
      .innerJoin(application).on(answer.application.eq(application))
      .innerJoin(study).on(application.study.eq(study))
      .innerJoin(member).on(answer.createdBy.eq(member.email))
      .innerJoin(studyMember).on(member.eq(studyMember.member), study.eq(studyMember.study))
      .innerJoin(interview).on(studyMember.eq(interview.studyMember))
      .where(
        study.generation.value.eq(request.generation()),
        statusEq(request.status()),
        answer.confirmYN.isTrue()
      );

    return PageableExecutionUtils.getPage(answerList, request.pageRequest(), countQuery::fetchOne);
  }

  private BooleanExpression statusEq(StudyMemberStatus status) {
    if (status == null) {
      return null;
    }
    return studyMember.status.eq(status);
  }

}
