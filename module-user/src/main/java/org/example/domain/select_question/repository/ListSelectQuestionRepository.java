package org.example.domain.select_question.repository;

import static org.example.domain.application.QApplication.application;
import static org.example.domain.select_question.QSelectQuestion.selectQuestion;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.select_question.controller.response.DetailSelectQuestionDto;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ListSelectQuestionRepository {

  private final JPAQueryFactory queryFactory;

  public List<DetailSelectQuestionDto> getSelectQuestionList(Long applicationId) {
    return queryFactory
      .select(Projections.fields(DetailSelectQuestionDto.class,
        selectQuestion.id.as("selectQuestionId"),
        selectQuestion.question,
        selectQuestion.isRequired,
        selectQuestion.isMultiSelect,
        selectQuestion.sequence
        )
      )
      .from(selectQuestion)
      .innerJoin(application).on(selectQuestion.application.eq(application))
      .where(
        application.id.eq(applicationId)
      )
      .fetch()
      ;
  }
}
