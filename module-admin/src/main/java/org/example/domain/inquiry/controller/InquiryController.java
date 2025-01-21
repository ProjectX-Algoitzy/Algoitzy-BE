package org.example.domain.inquiry.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.inquiry.controller.request.SearchInquiryRequest;
import org.example.domain.inquiry.controller.response.DetailInquiryResponse;
import org.example.domain.inquiry.controller.response.ListInquiryCategoryResponse;
import org.example.domain.inquiry.controller.response.ListInquiryResponse;
import org.example.domain.inquiry.service.InquiryService;
import org.example.domain.inquiry_reply.controller.request.SearchInquiryReplyRequest;
import org.example.domain.inquiry_reply.controller.response.ListInquiryReplyResponse;
import org.example.domain.inquiry_reply.service.InquiryReplyService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inquiry")
@RequiredArgsConstructor
@Tag(name = "InquiryController", description = "[ADMIN] 문의 관련 API")
public class InquiryController {

  private final InquiryService inquiryService;
  private final InquiryReplyService inquiryReplyService;

  @GetMapping("/category")
  @Operation(summary = "문의 카테고리 목록 조회")
  public ApiResponse<ListInquiryCategoryResponse> getInquiryCategoryList() {
    return ApiResponse.onSuccess(inquiryService.getInquiryCategoryList());
  }

  @GetMapping
  @Operation(summary = "문의 목록 조회")
  public ApiResponse<ListInquiryResponse> getInquiryCategoryList(
    @ParameterObject @ModelAttribute @Valid SearchInquiryRequest request) {
    return ApiResponse.onSuccess(inquiryService.getInquiryList(request));
  }

  @GetMapping("/{inquiry-id}")
  @Operation(summary = "문의 상세 조회")
  public ApiResponse<DetailInquiryResponse> getInquiry(@PathVariable("inquiry-id") Long inquiryId) {
    return ApiResponse.onSuccess(inquiryService.getInquiry(inquiryId));
  }

  @GetMapping("/{inquiry-id}/reply")
  @Operation(summary = "문의 댓글 목록 조회")
  public ApiResponse<ListInquiryReplyResponse> getInquiryReplyList(
    @PathVariable("inquiry-id") Long inquiryId,
    @ParameterObject @ModelAttribute @Valid SearchInquiryReplyRequest request) {
    return ApiResponse.onSuccess(inquiryReplyService.getInquiryReplyList(inquiryId, request));
  }
}
