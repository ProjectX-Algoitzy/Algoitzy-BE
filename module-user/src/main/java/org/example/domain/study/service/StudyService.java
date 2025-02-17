package org.example.domain.study.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.study.controller.request.CreateTempStudyRequest;
import org.example.domain.study.controller.request.UpdateStudyRequest;
import org.example.domain.study.controller.response.DetailTempStudyResponse;
import org.example.domain.study.controller.response.ListStudyResponse;
import org.example.domain.study.controller.response.RegularStudyInfoResponse;
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
   * 자율 스터디 생성
   */
  public void createTempStudy(CreateTempStudyRequest request) {
    createStudyService.createTempStudy(request);
  }

  /**
   * 자율 스터디 목록 조회
   */
  public ListStudyResponse getTempStudyList() {
    return listStudyService.getTempStudyList();
  }

  /**
   * 자율 스터디 상세 조회
   */
  public DetailTempStudyResponse getTempStudy(Long studyId) {
    return detailStudyService.getTempStudy(studyId);
  }

  /**
   * 자율 스터디 수정
   */
  public void updateStudy(Long studyId, UpdateStudyRequest request) {
    createStudyService.updateStudy(studyId, request);
  }

  /**
   * 자율 스터디 종료
   */
  public void endTempStudy(Long studyId) {
    createStudyService.endTempStudy(studyId);
  }

  /**
   * 자율 스터디 지원
   */
  public void applyTempStudy(Long studyId) {
    createStudyService.applyTempStudy(studyId);
  }


  /**
   * 자율 스터디원 수락
   */
  public void passTempStudy(Long studyMemberId) {
    createStudyService.passTempStudy(studyMemberId);
  }

  /**
   * 정규 스터디 목록 조회
   */
  public ListStudyResponse getRegularStudyList() {
    return listStudyService.getRegularStudyList();
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

  /**
   * 나의 스터디 목록 조회
   */
  public ListStudyResponse getMyStudyList() {
    return listStudyService.getMyStudyList();
  }
}
