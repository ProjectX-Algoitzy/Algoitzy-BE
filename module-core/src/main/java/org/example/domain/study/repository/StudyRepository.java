package org.example.domain.study.repository;

import java.util.Optional;
import org.example.domain.study.Study;
import org.example.domain.study.enums.StudyType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {

  Optional<Study> findByNameAndTypeIs(String name, StudyType type);
}
