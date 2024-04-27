package org.example.domain.text_answer.repository;

import static org.example.domain.answer.QAnswer.answer;
import static org.example.domain.text_answer.QTextAnswer.textAnswer;
import static org.example.domain.text_question.QTextQuestion.textQuestion;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.text_answer.response.DetailTextAnswerDto;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ListTextAnswerRepository {

  private final JPAQueryFactory queryFactory;

  public List<DetailTextAnswerDto> getTextAnswerList(Long answerId) {
    return queryFactory
      .select(Projections.fields(DetailTextAnswerDto.class,
          textQuestion.question,
          textAnswer.text
        )
      )
      .from(textAnswer)
      .innerJoin(answer).on(textAnswer.answer.eq(answer))
      .innerJoin(textQuestion).on(textAnswer.textQuestion.eq(textQuestion))
      .where(
        answer.id.eq(answerId)
      )
      .fetch();
  }
}
