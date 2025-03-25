package org.example.domain.inquiry.service;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.inquiry.controller.response.DetailInquiryResponse;
import org.example.domain.inquiry.repository.DetailInquiryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetailInquiryService {

  private final DetailInquiryRepository detailInquiryRepository;

  /**
   * 문의 상세 조회
   */
  public DetailInquiryResponse getInquiry(Long inquiryId) {
    DetailInquiryResponse response = detailInquiryRepository.getInquiry(inquiryId);
    if (response == null) throw new GeneralException(ErrorStatus.PAGE_NOT_FOUND, "존재하지 않는 문의입니다.");
    response.updateCategoryName();

    return response;
  }

}
