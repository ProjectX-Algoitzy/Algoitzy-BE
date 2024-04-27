package org.example.domain.select_answer.repository;

import java.util.List;
import org.example.domain.select_answer.SelectAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SelectAnswerRepository extends JpaRepository<SelectAnswer, Long> {

  List<SelectAnswer> findAllByAnswerId(Long answerId);
}
