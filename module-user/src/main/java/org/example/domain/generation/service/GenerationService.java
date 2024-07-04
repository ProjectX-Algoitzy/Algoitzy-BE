package org.example.domain.generation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenerationService {

  private final DetailGenerationService detailGenerationService;

  /**
   * 최신 기수
   */
  public Integer getMaxGeneration() {
    return detailGenerationService.getMaxGeneration();
  }
}
