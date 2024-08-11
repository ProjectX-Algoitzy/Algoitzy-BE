package org.example.domain.answer.repository;


import static org.example.domain.answer.QAnswer.answer;
import static org.example.domain.application.QApplication.application;
import static org.example.domain.member.QMember.member;
import static org.example.domain.study.QStudy.study;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.domain.answer.controller.response.DetailAnswerResponse;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DetailAnswerRepository {

  private final JPAQueryFactory queryFactory;

  public DetailAnswerResponse getAnswer(Long answerId) {
    return queryFactory
      .select(Projections.fields(DetailAnswerResponse.class,
          answer.id.as("answerId"),
          study.id.as("studyId"),
          study.name.as("studyName"),
          member.name.as("submitName"),
          member.email.as("submitEmail"),
          member.phoneNumber,
          answer.updatedTime.as("submitTime")
        )
      )
      .from(answer)
      .innerJoin(application).on(answer.application.eq(application))
      .innerJoin(study).on(application.study.eq(study))
      .innerJoin(member).on(answer.createdBy.eq(member.email))
      .where(
        answer.id.eq(answerId),
        answer.confirmYN.isTrue()
      )
      .fetchOne();
  }

}
