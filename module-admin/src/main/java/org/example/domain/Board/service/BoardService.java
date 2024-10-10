package org.example.domain.Board.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.Board.controller.request.CreateBoardRequest;
import org.example.domain.Board.controller.request.SearchBoardRequest;
import org.example.domain.Board.controller.response.ListBoardResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

  private final CreateBoardService createBoardService;
  private final ListBoardService listBoardService;

  /**
   * 공지사항 게시글 생성
   */
  public void createBoard(CreateBoardRequest request) {
    createBoardService.createBoard(request);
  }

  /**
   * 게시글 목록 조회
   */
  public ListBoardResponse getBoardList(SearchBoardRequest request) {
    return listBoardService.getBoardList(request);
  }
}
