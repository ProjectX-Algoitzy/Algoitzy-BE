package org.example.domain.text_question.repository;

import org.example.domain.text_question.TextQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextQuestionRepository extends JpaRepository<TextQuestion, Long> {

}
