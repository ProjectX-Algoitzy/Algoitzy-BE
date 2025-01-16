package org.example.domain.inquiry_reply.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.inquiry_reply.controller.request.CreateInquiryReplyRequest;
import org.example.domain.inquiry_reply.controller.request.SearchInquiryReplyRequest;
import org.example.domain.inquiry_reply.controller.request.UpdateInquiryReplyRequest;
import org.example.domain.inquiry_reply.controller.response.ListInquiryReplyResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InquiryReplyService {

  private final ListInquiryReplyService listInquiryReplyService;
  private final CreateInquiryReplyService createInquiryReplyService;

  /**
   * 문의 댓글 목록 조회
   */
  public ListInquiryReplyResponse getInquiryReplyList(Long inquiryId, SearchInquiryReplyRequest request) {
    return listInquiryReplyService.getInquiryReplyList(inquiryId, request);
  }

  /**
   * 문의 댓글/대댓글 생성
   */
  public void createInquiryReply(CreateInquiryReplyRequest request) {
    createInquiryReplyService.createInquiryReply(request);
  }

  /**
   * 문의 댓글 수정
   */
  public void updateInquiryReply(Long replyId, UpdateInquiryReplyRequest request) {
    createInquiryReplyService.updateInquiryReply(replyId, request);
  }

  /**
   * 문의 댓글 삭제
   */
  public void deleteInquiryReply(Long replyId) {
    createInquiryReplyService.deleteInquiryReply(replyId);
  }


}
