package org.example.domain.text_answer.repository;

import static org.example.domain.application.QApplication.application;
import static org.example.domain.text_question.QTextQuestion.textQuestion;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.text_answer.controller.response.DetailTextAnswerDto;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ListTextAnswerRepository {

  private final JPAQueryFactory queryFactory;

  /**
   * 지원서 상세 주관식 답변 목록 조회
   */
  public List<DetailTextAnswerDto> getTextQuestionList(Long applicationId) {
    return queryFactory
      .select(Projections.fields(
          DetailTextAnswerDto.class,
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
