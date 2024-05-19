package org.example.domain.study.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.study.controller.request.CreateTempStudyRequest;
import org.example.domain.study.controller.response.ListTempStudyResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudyService {

  private final CreateStudyService createStudyService;
  private final DetailStudyService detailStudyService;
  private final ListStudyService listStudyService;

  /**
   * 최신 기수 스터디 개수
   */
  public Integer getStudyCount() {
    return listStudyService.getStudyCount();
  }

  /**
   * 스터디 최신 기수
   */
  public Integer getMaxStudyGeneration() {
    return listStudyService.getMaxStudyGeneration();
  }

  /**
   * 자율 스터디 생성
   */
  public void createTempStudy(CreateTempStudyRequest request) {
    createStudyService.createTempStudy(request);
  }

  /**
   * 자율 스터디 목록 조회
   */
  public ListTempStudyResponse getTempStudyList() {
    return listStudyService.getTempStudyList();
  }
}
