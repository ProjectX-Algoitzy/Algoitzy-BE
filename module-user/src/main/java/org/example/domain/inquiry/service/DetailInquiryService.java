package org.example.domain.inquiry.service;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.inquiry.Inquiry;
import org.example.domain.inquiry.controller.response.DetailInquiryResponse;
import org.example.domain.inquiry.repository.DetailInquiryRepository;
import org.example.domain.member.Member;
import org.example.domain.member.service.CoreMemberService;
import org.example.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetailInquiryService {

  private final CoreInquiryService coreInquiryService;
  private final DetailInquiryRepository detailInquiryRepository;
  private final CoreMemberService coreMemberService;

  /**
   * 문의 상세 조회
   */
  @Transactional
  public DetailInquiryResponse getInquiry(Long inquiryId) {
    DetailInquiryResponse response = detailInquiryRepository.getInquiry(inquiryId);
    if (response == null) throw new GeneralException(ErrorStatus.PAGE_NOT_FOUND, "존재하지 않는 문의입니다.");
    response.updateCategoryName();

    // 타인의 비공개 문의 조회 시 오류
    Member currentMember = coreMemberService.findByEmail(SecurityUtils.getCurrentMemberEmail());
    if (!currentMember.getHandle().equals(response.getHandle()) && !response.isPublicYn())
      throw new GeneralException(ErrorStatus.PAGE_UNAUTHORIZED, "비공개 문의입니다.");

    Inquiry inquiry = coreInquiryService.findById(inquiryId);
    inquiry.addViewCount();

    return response;
  }

}
