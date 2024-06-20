package org.example.domain.study.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.attendance.controller.response.ListAttendanceResponse;
import org.example.domain.curriculum.controller.response.ListCurriculumResponse;
import org.example.domain.study.controller.request.CreateTempStudyRequest;
import org.example.domain.study.controller.response.DetailTempStudyResponse;
import org.example.domain.study.controller.response.ListRegularStudyResponse;
import org.example.domain.study.controller.response.ListTempStudyResponse;
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
    return null;
  }

  /**
   * 정규 스터디 정보 조회
   */
  public RegularStudyInfoResponse getRegularStudyInfo() {
    return null;
  }

  /**
   * 정규 스터디 홈 조회
   */
  public String getContent(Long studyId) {
    return null;
  }

  /**
   * 정규 스터디 커리큘럼 조회
   */
  public ListCurriculumResponse getCurriculumList(Long studyId) {
    return null;
  }

  /**
   * 정규 스터디 출석부 조회
   */
  public ListAttendanceResponse getAttendanceList(Long studyId) {
    return null;
  }
}
