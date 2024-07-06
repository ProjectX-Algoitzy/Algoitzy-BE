package org.example.domain.generation.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.generation.controller.request.RenewGenerationRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenerationService {

  private final CreateGenerationService createGenerationService;


  /**
   * 🚫기수 갱신🚫
   */
  public void renewGeneration(RenewGenerationRequest request) {
    createGenerationService.renewGeneration(request);
  }
}
