package org.example.domain.institution.service;

import static org.example.util.ValueUtils.INSTITUTION_VIEW_COUNT_KEY;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.institution.controller.response.DetailInstitutionResponse;
import org.example.domain.institution.repository.DetailInstitutionRepository;
import org.example.domain.member.enums.Role;
import org.example.domain.member.service.CoreMemberService;
import org.example.domain.study_member.repository.DetailStudyMemberRepository;
import org.example.util.RedisUtils;
import org.example.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetailInstitutionService {

  private final CoreMemberService coreMemberService;
  private final DetailStudyMemberRepository detailStudyMemberRepository;
  private final DetailInstitutionRepository detailInstitutionRepository;
  private final RedisUtils redisUtils;

  /**
   * 기관 상세 조회
   */
  public DetailInstitutionResponse getInstitution(Long institutionId) {
    if (!detailStudyMemberRepository.isRegularStudyMember()
    && coreMemberService.findByEmail(SecurityUtils.getCurrentMemberEmail()).getRole().equals(Role.ROLE_USER)) {
      throw new GeneralException(ErrorStatus.NOTICE_UNAUTHORIZED, "스터디원만 열람할 수 있습니다.");
    }

    redisUtils.addViewCount(INSTITUTION_VIEW_COUNT_KEY + institutionId);

    return Optional.ofNullable(detailInstitutionRepository.getInstitution(institutionId))
      .orElseThrow(() -> new GeneralException(ErrorStatus.PAGE_NOT_FOUND));
  }
}
