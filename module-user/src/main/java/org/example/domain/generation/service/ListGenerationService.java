package org.example.domain.generation.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.generation.controller.response.ListGenerationResponse;
import org.example.domain.generation.repository.ListGenerationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListGenerationService {

  private final ListGenerationRepository listGenerationRepository;

  /**
   * 기수 목록 조회
   */
  public ListGenerationResponse getGenerationList() {
    return ListGenerationResponse.builder()
      .generationList(listGenerationRepository.getGenerationList())
      .build();
  }
}
