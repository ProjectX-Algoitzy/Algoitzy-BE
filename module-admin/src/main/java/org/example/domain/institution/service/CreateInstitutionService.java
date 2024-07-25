package org.example.domain.institution.service;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.institution.Institution;
import org.example.domain.institution.controller.request.CreateInstitutionRequest;
import org.example.domain.institution.controller.request.UpdateInstitutionRequest;
import org.example.domain.institution.repository.InstitutionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateInstitutionService {

  private final CoreInstitutionService coreInstitutionService;
  private final InstitutionRepository institutionRepository;

  /**
   * 기관 생성
   */
  public void createInstitution(CreateInstitutionRequest request) {
    if (institutionRepository.findByName(request.name()).isPresent()) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "이미 존재하는 기관명입니다.");
    }

    institutionRepository.save(
      Institution.builder()
        .name(request.name())
        .content(request.content())
        .type(request.type())
        .build()
    );
  }

  /**
   * 기관 수정
   */
  public void updateInstitution(Long institutionId, UpdateInstitutionRequest request) {
    Institution institution = coreInstitutionService.findById(institutionId);
    institution.update(
      request.name(),
      request.content(),
      request.type()
    );
  }
}
