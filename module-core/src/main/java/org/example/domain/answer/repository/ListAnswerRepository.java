package org.example.domain.answer.repository;


import static org.example.domain.answer.QAnswer.*;
import static org.example.domain.application.QApplication.*;
import static org.example.domain.member.QMember.*;
import static org.example.domain.study.QStudy.*;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.answer.controller.request.SearchAnswerRequest;
import org.example.domain.answer.controller.response.ListAnswerDto;
import org.example.domain.member.repository.MemberAuthRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ListAnswerRepository {

  private final MemberAuthRepository memberAuthRepository;
  private final JPAQueryFactory queryFactory;

  public Page<ListAnswerDto> getAnswerList(SearchAnswerRequest request) {
    List<ListAnswerDto> answerList = queryFactory
      .select(Projections.fields(ListAnswerDto.class,
          answer.id.as("answerId"),
          study.name.as("studyName"),
          member.name.as("submitName"),
          answer.updatedTime.as("submitTime")
        )
      )
      .from(answer)
      .innerJoin(application).on(answer.application.eq(application))
      .innerJoin(study).on(application.study.eq(study))
      .innerJoin(member).on(answer.createdBy.eq(member.email))
      .where(
        memberAuthRepository.emailEq()
      )
      .offset(request.pageRequest().getOffset())
      .limit(request.pageRequest().getPageSize())
      .fetch();

    JPAQuery<Long> countQuery = queryFactory
      .select(answer.count())
      .from(answer)
      .innerJoin(application).on(answer.application.eq(application))
      .innerJoin(study).on(application.study.eq(study))
      .where(
        memberAuthRepository.emailEq()
      )
      .offset(request.pageRequest().getOffset())
      .limit(request.pageRequest().getPageSize());

    return PageableExecutionUtils.getPage(answerList, request.pageRequest(), countQuery::fetchOne);
  }

}
