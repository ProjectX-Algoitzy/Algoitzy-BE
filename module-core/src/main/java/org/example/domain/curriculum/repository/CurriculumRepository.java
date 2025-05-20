package org.example.domain.curriculum.repository;

import java.util.List;
import org.example.domain.curriculum.Curriculum;
import org.example.domain.study.Study;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {

  List<Curriculum> findAllByStudy(Study study);

  @Query("SELECT MAX(c.orderNumber) FROM Curriculum c WHERE c.study = :study")
  Integer findMaxOrderNumberByStudy(@Param("study") Study study);
}
