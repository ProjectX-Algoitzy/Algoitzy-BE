package org.example.domain.study.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.study.controller.request.CreateRegularStudyRequest;
import org.example.domain.study.controller.request.UpdateStudyRequest;
import org.example.domain.study.controller.response.DetailTempStudyResponse;
import org.example.domain.study.controller.response.ListRegularStudyResponse;
import org.example.domain.study.controller.response.RegularStudyInfoResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudyService {

  private final CreateStudyService createStudyService;
  private final ListStudyService listStudyService;
  private final DetailStudyService detailStudyService;

  /**
   * 자율 스터디 상세 조회
   */
  public DetailTempStudyResponse getTempStudy(Long studyId) {
    return detailStudyService.getTempStudy(studyId);
  }

  /**
   * 정규 스터디 목록 조회
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

  /**
   * 스터디 수정
   */
  public void updateStudy(Long studyId, UpdateStudyRequest request) {
    createStudyService.updateStudy(studyId, request);
  }

  /**
   * 정규 스터디 정보 조회
   */
  public RegularStudyInfoResponse getRegularStudyInfo(Long studyId) {
    return detailStudyService.getRegularStudyInfo(studyId);
  }

  /**
   * 정규 스터디 홈 조회
   */
  public String getContent(Long studyId) {
    return detailStudyService.getContent(studyId);
  }
}
