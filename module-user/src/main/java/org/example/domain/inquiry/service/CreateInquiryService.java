package org.example.domain.inquiry.service;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.inquiry.Inquiry;
import org.example.domain.inquiry.controller.request.CreateInquiryRequest;
import org.example.domain.inquiry.controller.request.UpdateInquiryRequest;
import org.example.domain.inquiry.repository.InquiryRepository;
import org.example.domain.member.Member;
import org.example.domain.member.enums.Role;
import org.example.domain.member.service.CoreMemberService;
import org.example.email.service.CoreEmailService;
import org.example.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateInquiryService {

  private final InquiryRepository inquiryRepository;
  private final CoreMemberService coreMemberService;
  private final CoreInquiryService coreInquiryService;
  private final CoreEmailService coreEmailService;

  /**
   * 문의 생성
   */
  public void createInquiry(CreateInquiryRequest request) {
    if (request.category() == null)
      throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "카테고리는 필수입니다.");
    if (!StringUtils.hasText(request.title()) || !StringUtils.hasText(request.content()))
      throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "제목 또는 내용이 비어있습니다.");

    Member member = coreMemberService.findByEmail(SecurityUtils.getCurrentMemberEmail());
    if (!member.getRole().equals(Role.ROLE_USER))
      throw new GeneralException(ErrorStatus.NOTICE_UNAUTHORIZED, "관리자는 작성할 수 없습니다.");

    inquiryRepository.save(
      Inquiry.builder()
        .title(request.title())
        .content(request.content())
        .category(request.category())
        .member(member)
        .publicYn(request.publicYn())
        .build()
    );

    // 관리자 모두에게 메일 발송
    coreEmailService.sendInquiryCreatedEmail(member);
  }

  /**
   * 문의 수정
   */
  public void updateInquiry(long inquiryId, UpdateInquiryRequest request) {
    if (request.category() == null)
      throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "카테고리는 필수입니다.");
    if (!StringUtils.hasText(request.title()) || !StringUtils.hasText(request.content()))
      throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "제목 또는 내용이 비어있습니다.");

    Inquiry inquiry = coreInquiryService.findById(inquiryId);
    if (!inquiry.getMember().equals(coreMemberService.findByEmail(SecurityUtils.getCurrentMemberEmail())))
      throw new GeneralException(ErrorStatus.UNAUTHORIZED, "자신이 남긴 문의 이외에는 수정할 수 없습니다.");

    inquiry.updateInquiry(
      request.category(),
      request.publicYn(),
      request.title(),
      request.content()
    );
  }

  /**
   * 문의 공개 여부 변경
   */
  public void updateInquiryPublic(long inquiryId) {
    Inquiry inquiry = coreInquiryService.findById(inquiryId);
    if (!inquiry.getMember().equals(coreMemberService.findByEmail(SecurityUtils.getCurrentMemberEmail())))
      throw new GeneralException(ErrorStatus.UNAUTHORIZED, "자신이 남긴 문의 이외에는 수정할 수 없습니다.");

    inquiry.updatePublicYn();
  }

  /**
   * 문의 삭제
   */
  public void deleteInquiry(long inquiryId) {
    Inquiry inquiry = coreInquiryService.findById(inquiryId);
    if (!inquiry.getMember().equals(coreMemberService.findByEmail(SecurityUtils.getCurrentMemberEmail())))
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "자신이 남긴 문의 이외에는 삭제할 수 없습니다.");
    if (inquiry.getSolvedYn())
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "답변이 등록된 문의는 삭제할 수 없습니다.");

    inquiryRepository.deleteById(inquiryId);
  }

}
