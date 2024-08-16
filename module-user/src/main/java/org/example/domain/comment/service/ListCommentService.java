package org.example.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.comment.controller.request.SearchCommentRequest;
import org.example.domain.comment.controller.response.ListCommentDto;
import org.example.domain.comment.controller.response.ListCommentResponse;
import org.example.domain.comment.repository.ListCommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListCommentService {

  private final ListCommentRepository listCommentRepository;

  /**
   * 댓글 목록 조회
   */
  public ListCommentResponse getCommentList(Long boardId, SearchCommentRequest request) {
    Page<ListCommentDto> page = listCommentRepository.getCommentList(boardId, request);
    for (ListCommentDto dto : page) {
      dto.encryptCreatedName();
    }

    return ListCommentResponse.builder()
      .commentList(page.getContent())
      .totalCount(page.getTotalElements())
      .build();
  }
}
