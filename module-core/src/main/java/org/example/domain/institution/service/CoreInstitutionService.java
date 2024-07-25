package org.example.domain.institution.service;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.institution.Institution;
import org.example.domain.institution.repository.InstitutionRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoreInstitutionService {

  private final InstitutionRepository institutionRepository;

  public Institution findById(Long institutionId) {
    return institutionRepository.findById(institutionId)
      .orElseThrow(() -> new GeneralException(ErrorStatus.NOT_FOUND, "존재하지 않는 기관 ID입니다."));
  }
}
