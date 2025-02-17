package org.example.domain.institution.service;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.institution.controller.response.DetailInstitutionResponse;
import org.example.domain.institution.repository.DetailInstitutionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetailInstitutionService {
  private final DetailInstitutionRepository detailInstitutionRepository;

  /**
   * 기관 상세 조회
   */
  public DetailInstitutionResponse getInstitution(Long institutionId) {
    DetailInstitutionResponse response = detailInstitutionRepository.getInstitution(institutionId);
    if (response == null) throw new GeneralException(ErrorStatus.PAGE_NOT_FOUND, "존재하지 않는 기관입니다.");
    response.updateType(response.getType());
    return response;
  }
}
