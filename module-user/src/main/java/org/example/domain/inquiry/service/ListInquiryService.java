package org.example.domain.inquiry.service;

import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.example.domain.inquiry.controller.request.SearchInquiryRequest;
import org.example.domain.inquiry.controller.response.ListInquiryCategoryDto;
import org.example.domain.inquiry.controller.response.ListInquiryCategoryResponse;
import org.example.domain.inquiry.controller.response.ListInquiryDto;
import org.example.domain.inquiry.controller.response.ListInquiryResponse;
import org.example.domain.inquiry.enums.InquiryCategory;
import org.example.domain.inquiry.repository.ListInquiryRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListInquiryService {

  private final ListInquiryRepository listInquiryRepository;

  /**
   * 문의 카테고리 목록 조회
   */
  public ListInquiryCategoryResponse getInquiryCategoryList() {
    List<ListInquiryCategoryDto> categoryList = Stream.of(InquiryCategory.values())
      .map(category -> ListInquiryCategoryDto.builder()
        .code(category.name())
        .name(category.getName())
        .build())
      .toList();

    return ListInquiryCategoryResponse.builder()
      .categoryList(categoryList)
      .build();
  }

  /**
   * 게시글 목록 조회
   */
  public ListInquiryResponse getInquiryList(SearchInquiryRequest request) {
    Page<ListInquiryDto> inquiryList = listInquiryRepository.getInquiryList(request);
    for (ListInquiryDto inquiry : inquiryList) inquiry.updateCategoryName();

    return ListInquiryResponse.builder()
      .inquiryList(inquiryList.getContent())
      .totalCount(inquiryList.getTotalElements())
      .build();
  }

}
