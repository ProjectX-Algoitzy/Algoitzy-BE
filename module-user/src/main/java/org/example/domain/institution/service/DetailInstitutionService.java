package org.example.domain.institution.service;

import static org.example.util.ValueUtils.INSTITUTION_VIEW_COUNT_KEY;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.institution.Institution;
import org.example.domain.institution.controller.response.DetailInstitutionResponse;
import org.example.domain.institution.repository.DetailInstitutionRepository;
import org.example.domain.study_member.repository.DetailStudyMemberRepository;
import org.example.util.RedisUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetailInstitutionService {

  private final CoreInstitutionService coreInstitutionService;
  private final DetailStudyMemberRepository detailStudyMemberRepository;
  private final DetailInstitutionRepository detailInstitutionRepository;
  private final RedisUtils redisUtils;

  /**
   * 기관 상세 조회
   */
  public DetailInstitutionResponse getInstitution(Long institutionId) {
    if (!detailStudyMemberRepository.isRegularStudyMember()) {
      throw new GeneralException(ErrorStatus.NOTICE_UNAUTHORIZED, "스터디원만 열람할 수 있습니다.");
    }

    int viewCount = Integer.parseInt(Optional.ofNullable(redisUtils.getValue(INSTITUTION_VIEW_COUNT_KEY + institutionId))
      .orElse("0"));
    redisUtils.save(INSTITUTION_VIEW_COUNT_KEY + institutionId, Integer.toString(viewCount + 1));

    Institution institution = coreInstitutionService.findById(institutionId);
    return detailInstitutionRepository.getInstitution(institution.getId());
  }
}
