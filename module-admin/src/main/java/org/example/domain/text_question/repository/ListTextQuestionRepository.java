package org.example.domain.text_question.repository;

import static org.example.domain.application.QApplication.application;
import static org.example.domain.text_question.QTextQuestion.textQuestion;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.text_question.controller.response.DetailTextQuestionDto;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ListTextQuestionRepository {

  private final JPAQueryFactory queryFactory;

  public List<DetailTextQuestionDto> getTextQuestionList(Long applicationId) {
    return queryFactory
      .select(Projections.fields(DetailTextQuestionDto.class,
        textQuestion.id.as("textQuestionId"),
        textQuestion.question,
        textQuestion.isRequired,
        textQuestion.sequence
        )
      )
      .from(textQuestion)
      .innerJoin(application).on(textQuestion.application.eq(application))
      .where(
        application.id.eq(applicationId)
      )
      .fetch();
  }
}
