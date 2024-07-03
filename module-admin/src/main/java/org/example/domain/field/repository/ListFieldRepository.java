package org.example.domain.field.repository;

import static org.example.domain.field.QField.field;
import static org.example.domain.select_question.QSelectQuestion.selectQuestion;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.field.controller.response.DetailFieldDto;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ListFieldRepository {

  private final JPAQueryFactory queryFactory;

  public List<DetailFieldDto> getFieldList(Long selectQuestionId) {
    return queryFactory
      .select(Projections.fields(DetailFieldDto.class,
        field.id.as("fieldId"),
        field.context
        )
      )
      .from(field)
      .innerJoin(selectQuestion).on(field.selectQuestion.eq(selectQuestion))
      .where(
        selectQuestion.id.eq(selectQuestionId)
      )
      .fetch()
      ;
  }
}
