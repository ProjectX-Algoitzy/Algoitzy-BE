package org.example.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.Board.Board;
import org.example.domain.Board.service.CoreBoardService;
import org.example.domain.comment.Comment;
import org.example.domain.comment.controller.request.CreateCommentRequest;
import org.example.domain.comment.controller.request.UpdateCommentRequest;
import org.example.domain.comment.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateCommentService {

  private final CoreBoardService coreBoardService;
  private final CoreCommentService coreCommentService;
  private final CommentRepository commentRepository;

  /**
   * 댓글 생성
   */
  public void createComment(String boardId, CreateCommentRequest request) {
    Board board = coreBoardService.findById(boardId);
    commentRepository.save(
      Comment.builder()
        .board(board)
        .content(request.content())
        .build()
    );
  }

  /**
   * 댓글 수정
   */
  public void updateComment(Long commentId, UpdateCommentRequest request) {
    Comment comment = coreCommentService.findById(commentId);
    comment.update(request.content());
  }

  /**
   * 댓글 삭제
   */
  public void deleteComment(Long commentId) {
    Comment comment = coreCommentService.findById(commentId);
    commentRepository.delete(comment);
  }
}
