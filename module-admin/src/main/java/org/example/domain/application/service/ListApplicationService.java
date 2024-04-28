package org.example.domain.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.application.controller.request.SearchApplicationRequest;
import org.example.domain.application.controller.response.ListApplicationDto;
import org.example.domain.application.controller.response.ListApplicationResponse;
import org.example.domain.application.repository.ListApplicationRepository;
import org.example.domain.study.service.CoreStudyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListApplicationService {

  private final ListApplicationRepository listApplicationRepository;


  /**
   * 지원서 양식 목록 조회
   */
  public ListApplicationResponse getApplicationList(SearchApplicationRequest request) {
    List<ListApplicationDto> applicationList = listApplicationRepository.getApplicationList(request);

    return ListApplicationResponse.builder()
      .applicationList(applicationList)
      .build();
  }
}
