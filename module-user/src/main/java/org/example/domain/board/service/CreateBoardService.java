package org.example.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.Board.Board;
import org.example.domain.Board.repository.BoardRepository;
import org.example.domain.board.controller.request.CreateBoardRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateBoardService {

  private final BoardRepository boardRepository;

  public void createBoard(CreateBoardRequest request) {
    boardRepository.save(
      Board.builder()
        .title(request.title())
        .content(request.content())
        .build()
    );
  }
}
