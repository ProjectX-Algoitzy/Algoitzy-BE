package org.example.domain.generation.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.generation.Generation;
import org.example.domain.generation.controller.response.DetailGenerationResponse;
import org.example.domain.generation.repository.GenerationRepository;
import org.example.domain.week.controller.response.ListWeekDto;
import org.example.domain.week.repository.ListWeekRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetailGenerationService {

  private final GenerationRepository generationRepository;
  private final ListWeekRepository listWeekRepository;
  
  public DetailGenerationResponse getGeneration() {
    Generation generation = generationRepository.findTopByOrderByValueDesc();
    List<ListWeekDto> weekList = listWeekRepository.getWeekList(generation);

    return DetailGenerationResponse.builder()
      .generation(generation.getValue())
      .weekList(weekList)
      .build();
  }
}
