package org.example.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.comment.Comment;
import org.example.domain.comment.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CoreCommentService {

  private final CommentRepository commentRepository;

  public Comment findById(Long commentId) {
    return commentRepository.findById(commentId)
      .orElseThrow(() -> new GeneralException(ErrorStatus.KEY_NOT_EXIST, "존재하지 않는 댓글 ID입니다."));

  }
}
