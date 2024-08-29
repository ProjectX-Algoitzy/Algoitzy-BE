package org.example.domain.application.repository;

import java.util.Optional;
import org.example.domain.application.Application;
import org.example.domain.study.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

  Optional<Application> findByStudy(Study study);
}
