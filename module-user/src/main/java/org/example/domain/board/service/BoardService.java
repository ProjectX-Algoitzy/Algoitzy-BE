package org.example.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.board.controller.request.CreateBoardRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

  private final CreateBoardService createBoardService;

  public void createBoard(CreateBoardRequest request) {
    createBoardService.createBoard(request);
  }
}
