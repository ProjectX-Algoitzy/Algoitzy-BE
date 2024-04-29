package org.example.domain.text_question.repository;

import java.util.List;
import org.example.domain.application.Application;
import org.example.domain.text_question.TextQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextQuestionRepository extends JpaRepository<TextQuestion, Long> {

  List<TextQuestion> findAllByApplicationAndIsRequiredIsTrue(Application application);
}
