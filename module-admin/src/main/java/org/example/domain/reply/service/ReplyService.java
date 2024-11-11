package org.example.domain.reply.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.reply.controller.request.SearchReplyRequest;
import org.example.domain.reply.controller.response.ListReplyResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyService {

  private final CreateReplyService createReplyService;
  private final ListReplyService listReplyService;

  /**
   * 게시글 댓글 목록 조회
   */
  public ListReplyResponse getReplyList(Long boardId, SearchReplyRequest request) {
    return listReplyService.getReplyList(boardId, request);
  }

  /**
   * 댓글 삭제
   */
  public void deleteReply(Long replyId) {
    createReplyService.deleteReply(replyId);
  }
}
