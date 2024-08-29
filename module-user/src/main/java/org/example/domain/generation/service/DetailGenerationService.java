package org.example.domain.generation.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.generation.repository.GenerationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetailGenerationService {

  private final GenerationRepository generationRepository;

  /**
   * 최신 기수
   */
  public Integer getMaxGeneration() {
    return generationRepository.findTopByOrderByValueDesc().getValue();
  }
}
