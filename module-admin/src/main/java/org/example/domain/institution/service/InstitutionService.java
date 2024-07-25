package org.example.domain.institution.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.institution.controller.request.CreateInstitutionRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstitutionService {

  private final CreateInstitutionService createInstitutionService;

  /**
   * 기관 생성
   */
  public void createInstitution(CreateInstitutionRequest request) {
    createInstitutionService.createInstitution(request);
  }
}
