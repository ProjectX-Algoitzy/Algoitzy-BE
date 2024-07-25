package org.example.domain.institution.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.institution.controller.request.SearchInstitutionRequest;
import org.example.domain.institution.controller.response.ListInstitutionDto;
import org.example.domain.institution.controller.response.ListInstitutionResponse;
import org.example.domain.institution.repository.ListInstitutionRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListInstitutionService {

  private final ListInstitutionRepository listInstitutionRepository;

  public ListInstitutionResponse getInstitutionList(SearchInstitutionRequest request) {
    Page<ListInstitutionDto> page = listInstitutionRepository.getInstitutionList(request);
    return ListInstitutionResponse.builder()
      .institutionList(page.getContent())
      .totalCount(page.getTotalElements())
      .build();
  }
}
