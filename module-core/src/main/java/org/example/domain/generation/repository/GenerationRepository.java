package org.example.domain.generation.repository;

import org.example.domain.generation.Generation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenerationRepository extends JpaRepository<Generation, Long> {

  Generation findTopByOrderByValueDesc();
}
