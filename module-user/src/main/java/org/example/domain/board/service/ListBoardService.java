package org.example.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.board.controller.request.SearchBoardRequest;
import org.example.domain.board.controller.response.ListBoardDto;
import org.example.domain.board.controller.response.ListBoardResponse;
import org.example.domain.board.repository.ListBoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListBoardService {

  private final ListBoardRepository listBoardRepository;

  /**
   * 게시판 목록 조회
   */
  public ListBoardResponse getBoardList(SearchBoardRequest request) {
    Page<ListBoardDto> page = listBoardRepository.getBoardList(request);

    return ListBoardResponse.builder()
      .boardList(page.getContent())
      .totalCount(page.getTotalElements())
      .build();
  }
}
