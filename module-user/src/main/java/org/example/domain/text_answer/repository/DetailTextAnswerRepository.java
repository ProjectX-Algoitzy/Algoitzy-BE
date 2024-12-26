package org.example.domain.text_answer.repository;

import static org.example.domain.text_answer.QTextAnswer.textAnswer;
import static org.example.domain.text_question.QTextQuestion.textQuestion;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.util.SecurityUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DetailTextAnswerRepository {

  private final JPAQueryFactory queryFactory;

  /**
   * 지원서 상세 주관식 답변 목록 조회
   */
  public String getTextAnswer(Long textQuestionId, Long answerId) {
    return queryFactory
      .select(textAnswer.text)
      .from(textAnswer)
      .innerJoin(textQuestion).on(textAnswer.textQuestion.eq(textQuestion))
      .where(
        textQuestion.id.eq(textQuestionId),
        textAnswer.answer.id.eq(answerId),
        textAnswer.createdBy.eq(SecurityUtils.getCurrentMemberEmail())
      )
      .fetchOne();
  }
}
