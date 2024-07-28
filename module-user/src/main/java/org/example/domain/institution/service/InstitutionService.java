package org.example.domain.institution.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.institution.controller.request.SearchInstitutionRequest;
import org.example.domain.institution.controller.response.DetailInstitutionResponse;
import org.example.domain.institution.controller.response.ListInstitutionResponse;
import org.example.domain.workbook.controller.response.ListInstitutionWorkbookResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstitutionService {

  private final ListInstitutionService listInstitutionService;
  private final DetailInstitutionService detailInstitutionService;

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


  /**
   * 기관 문제집 목록 조회
   */
  public ListInstitutionWorkbookResponse getInstitutionWorkbookList(Long institutionId) {
    return listInstitutionService.getInstitutionWorkbookList(institutionId);
  }
}
