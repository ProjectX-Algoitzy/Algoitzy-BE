package org.example.domain.generation.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.generation.controller.response.ListGenerationResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenerationService {

  private final DetailGenerationService detailGenerationService;
  private final ListGenerationService listGenerationService;

  /**
   * 최신 기수
   */
  public Integer getMaxGeneration() {
    return detailGenerationService.getMaxGeneration();
  }

  /**
   * 기수 목록 조회
   */
  public ListGenerationResponse getGenerationList() {
    return listGenerationService.getGenerationList();
  }
}
