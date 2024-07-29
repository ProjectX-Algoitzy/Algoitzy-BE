package org.example.domain.institution.service;

import static org.example.util.ValueUtils.INSTITUTION_VIEW_COUNT_KEY;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
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
    int viewCount = Integer.parseInt(Optional.ofNullable(redisUtils.getValue(INSTITUTION_VIEW_COUNT_KEY + institutionId))
      .orElse("0"));
    redisUtils.save(INSTITUTION_VIEW_COUNT_KEY + institutionId, Integer.toString(viewCount + 1));

    return detailInstitutionRepository.getInstitution(institutionId);
  }
}
