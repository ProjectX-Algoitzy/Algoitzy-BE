package org.example.domain.curriculum.repository;

import java.util.List;
import org.example.domain.curriculum.Curriculum;
import org.example.domain.study.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {

  List<Curriculum> findAllByStudy(Study study);
}
