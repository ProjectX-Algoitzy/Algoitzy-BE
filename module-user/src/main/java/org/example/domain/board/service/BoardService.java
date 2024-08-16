package org.example.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.board.controller.request.CreateBoardRequest;
import org.example.domain.board.controller.request.SearchBoardRequest;
import org.example.domain.board.controller.response.ListBoardResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

  private final CreateBoardService createBoardService;
  private final ListBoardService listBoardService;

  public void createBoard(CreateBoardRequest request) {
    createBoardService.createBoard(request);
  }

  public ListBoardResponse getBoardList(SearchBoardRequest request) {
    return listBoardService.getBoardList(request);
  }
}
