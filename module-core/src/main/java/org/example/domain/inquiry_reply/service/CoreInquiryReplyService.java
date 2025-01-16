package org.example.domain.inquiry_reply.service;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.inquiry_reply.InquiryReply;
import org.example.domain.inquiry_reply.repository.InquiryReplyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CoreInquiryReplyService {

  private final InquiryReplyRepository inquiryReplyRepository;

  public InquiryReply findById(Long replyId) {
    return inquiryReplyRepository.findById(replyId)
      .orElseThrow(() -> new GeneralException(ErrorStatus.KEY_NOT_EXIST, "존재하지 않는 댓글 ID입니다."));
  }

}
