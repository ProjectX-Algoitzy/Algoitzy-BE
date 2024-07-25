package org.example.domain.institution.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.institution.controller.request.CreateInstitutionRequest;
import org.example.domain.institution.controller.request.SearchInstitutionRequest;
import org.example.domain.institution.controller.response.DetailInstitutionResponse;
import org.example.domain.institution.controller.response.ListInstitutionResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstitutionService {

  private final CreateInstitutionService createInstitutionService;
  private final ListInstitutionService listInstitutionService;
  private final DetailInstitutionService detailInstitutionService;

  /**
   * 기관 생성
   */
  public void createInstitution(CreateInstitutionRequest request) {
    createInstitutionService.createInstitution(request);
  }

  /**
   * 기관 목록 조회
   */
  public ListInstitutionResponse getInstitutionList(SearchInstitutionRequest request) {
    return listInstitutionService.getInstitutionList(request);
  }

  /**
   * 기관 상세 조회
   */
  public DetailInstitutionResponse getInstitution(Long institutionId) {
    return detailInstitutionService.getInstitution(institutionId);
  }
}
