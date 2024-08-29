package org.example.domain.field.repository;

import java.util.List;
import org.example.domain.field.Field;
import org.example.domain.select_question.SelectQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FieldRepository extends JpaRepository<Field, Long> {

  List<Field> findAllBySelectQuestion(SelectQuestion selectQuestion);
}
