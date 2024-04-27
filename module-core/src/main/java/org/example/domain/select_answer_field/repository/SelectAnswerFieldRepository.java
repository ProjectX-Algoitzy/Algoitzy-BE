package org.example.domain.select_answer_field.repository;

import java.util.List;
import org.example.domain.select_answer.SelectAnswer;
import org.example.domain.select_answer_field.SelectAnswerField;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SelectAnswerFieldRepository extends JpaRepository<SelectAnswerField, Long> {

  List<SelectAnswerField> findAllBySelectAnswer(SelectAnswer selectAnswer);
}
