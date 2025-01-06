package org.example.domain.institution.service;

import static org.example.util.ValueUtils.INSTITUTION_VIEW_COUNT_KEY;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.institution.controller.response.DetailInstitutionResponse;
import org.example.domain.institution.repository.DetailInstitutionRepository;
import org.example.util.RedisUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetailInstitutionService {

  private final DetailInstitutionRepository detailInstitutionRepository;
  private final RedisUtils redisUtils;

  /**
   * 기관 상세 조회
   */
  public DetailInstitutionResponse getInstitution(Long institutionId) {
    redisUtils.addViewCount(INSTITUTION_VIEW_COUNT_KEY + institutionId);

    return Optional.ofNullable(detailInstitutionRepository.getInstitution(institutionId))
      .orElseThrow(() -> new GeneralException(ErrorStatus.PAGE_NOT_FOUND, "존재하지 않는 기관입니다."));
  }
}
