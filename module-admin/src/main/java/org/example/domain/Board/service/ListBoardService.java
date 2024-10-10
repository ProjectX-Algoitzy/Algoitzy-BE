package org.example.domain.Board.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.Board.controller.request.SearchBoardRequest;
import org.example.domain.Board.controller.response.ListBoardDto;
import org.example.domain.Board.controller.response.ListBoardResponse;
import org.example.domain.Board.repository.ListBoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListBoardService {

  private final ListBoardRepository listBoardRepository;

  /**
   * 게시글 목록 조회
   */
  public ListBoardResponse getBoardList(SearchBoardRequest request) {
    Page<ListBoardDto> boardList = listBoardRepository.getBoardList(request);
    boardList.forEach(board -> board.updateCategory(board.getCategory()));

    return ListBoardResponse.builder()
      .boardList(boardList.getContent())
      .totalCount(boardList.getTotalElements())
      .build();
  }
}
