package org.example.domain.select_answer_field.repository;

import static org.example.domain.field.QField.field;
import static org.example.domain.select_answer.QSelectAnswer.selectAnswer;
import static org.example.domain.select_answer_field.QSelectAnswerField.selectAnswerField;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.answer.Answer;
import org.example.domain.select_answer_field.SelectAnswerField;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ListSelectAnswerFieldRepository {

  private final JPAQueryFactory queryFactory;

  /**
   * 지원서 상세 객관식 문항 선택한 필드 목록 조회
   */
  public List<SelectAnswerField> getSelectAnswerFieldList(Answer answer) {
    return queryFactory
      .selectFrom(selectAnswerField)
      .innerJoin(field).on(selectAnswerField.field.eq(field))
      .innerJoin(selectAnswer).on(selectAnswerField.selectAnswer.eq(selectAnswer))
      .where(selectAnswer.answer.eq(answer))
      .fetch();
  }
}
