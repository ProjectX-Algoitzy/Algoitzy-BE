package org.example.domain.select_question.repository;

import java.util.List;
import org.example.domain.application.Application;
import org.example.domain.select_question.SelectQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SelectQuestionRepository extends JpaRepository<SelectQuestion, Long> {

  List<SelectQuestion> findAllByApplication(Application application);

  List<SelectQuestion> findAllByApplicationAndIsRequiredIsTrue(Application application);
}
