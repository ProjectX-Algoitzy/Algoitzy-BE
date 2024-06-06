package org.example.domain.study.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.study.controller.request.CreateRegularStudyRequest;
import org.example.domain.study.controller.response.ListRegularStudyResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudyService {

  private final CreateStudyService createStudyService;
  private final ListStudyService listStudyService;

  /**
   * 커리큘럼 목록 조회(드롭박스용)
   */
  public ListRegularStudyResponse getRegularStudyList() {
    return listStudyService.getRegularStudyList();
  }

  /**
   * 정규 스터디 생성
   */
  public void createRegularStudy(CreateRegularStudyRequest request) {
    createStudyService.createRegularStudy(request);
  }
}
