package org.example.domain.generation.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.generation.controller.request.RenewGenerationRequest;
import org.example.domain.generation.controller.response.DetailGenerationResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenerationService {

  private final CreateGenerationService createGenerationService;
  private final DetailGenerationService detailGenerationService;


  /**
   * 🚫기수 갱신🚫
   */
  public void renewGeneration(RenewGenerationRequest request) {
    createGenerationService.renewGeneration(request);
  }

  public DetailGenerationResponse getGeneration() {
    return detailGenerationService.getGeneration();
  }
}
