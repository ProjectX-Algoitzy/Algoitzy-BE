package org.example.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.comment.controller.request.CreateCommentRequest;
import org.example.domain.comment.controller.request.SearchCommentRequest;
import org.example.domain.comment.controller.request.UpdateCommentRequest;
import org.example.domain.comment.controller.response.ListCommentResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CreateCommentService createCommentService;
  private final ListCommentService listCommentService;

  /**
   * 댓글 생성
   */
  public void createComment(Long boardId, CreateCommentRequest request) {
    createCommentService.createComment(boardId, request);
  }

  /**
   * 댓글 목록 조회
   */
  public ListCommentResponse getCommentList(Long boardId, SearchCommentRequest request) {
    return listCommentService.getCommentList(boardId, request);
  }

  /**
   * 댓글 수정
   */
  public void updateComment(Long commentId, UpdateCommentRequest request) {
    createCommentService.updateComment(commentId, request);
  }

  /**
   * 댓글 삭제
   */
  public void deleteComment(Long commentId) {
    createCommentService.deleteComment(commentId);

  }
}
