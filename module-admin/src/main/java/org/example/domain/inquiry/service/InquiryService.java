package org.example.domain.inquiry.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.inquiry.controller.request.SearchInquiryRequest;
import org.example.domain.inquiry.controller.response.DetailInquiryResponse;
import org.example.domain.inquiry.controller.response.ListInquiryCategoryResponse;
import org.example.domain.inquiry.controller.response.ListInquiryResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InquiryService {

  private final ListInquiryService listInquiryService;
  private final DetailInquiryService detailInquiryService;

  /**
   * 문의 카테고리 목록 조회
   */
  public ListInquiryCategoryResponse getInquiryCategoryList() {
    return listInquiryService.getInquiryCategoryList();
  }

  /**
   * 문의 목록 조회
   */
  public ListInquiryResponse getInquiryList(SearchInquiryRequest request) {
    return listInquiryService.getInquiryList(request);
  }

  /**
   * 문의 상세 조회
   */
  public DetailInquiryResponse getInquiry(Long inquiryId) {
    return detailInquiryService.getInquiry(inquiryId);
  }

}
