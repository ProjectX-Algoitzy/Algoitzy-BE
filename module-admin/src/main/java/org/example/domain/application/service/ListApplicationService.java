package org.example.domain.application.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.application.controller.response.ListApplicationByGenerationDto;
import org.example.domain.application.controller.response.ListApplicationDto;
import org.example.domain.application.controller.response.ListApplicationResponse;
import org.example.domain.application.repository.ListApplicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListApplicationService {

  private final ListApplicationRepository listApplicationRepository;

  /**
   * 근 4기수 지원서 양식 목록 조회
   */
  public ListApplicationResponse getApplicationList() {
    List<ListApplicationDto> applicationList = new ArrayList<>();

    int maxGeneration = listApplicationRepository.getMaxStudyGeneration();
    for (int generation = maxGeneration; generation >= maxGeneration - 3; generation--) {
      List<ListApplicationByGenerationDto> applicationByGenerationList = listApplicationRepository.getApplicationList(generation);
      applicationList.add(ListApplicationDto.builder()
        .generation(generation)
        .applicationByGenerationList(applicationByGenerationList)
        .build()
      );
    }

    return ListApplicationResponse.builder()
      .applicationList(applicationList)
      .build();
  }
}
