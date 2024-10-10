package org.example.domain.Board.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.Board.controller.request.CreateBoardRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

  private final CreateBoardService createBoardService;

  /**
   * 공지사항 게시글 생성
   */
  public void createBoard(CreateBoardRequest request) {
    createBoardService.createBoard(request);
  }
}
