package org.example.domain.Board.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.Board.Board;
import org.example.domain.Board.repository.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CoreBoardService {

  private final BoardRepository boardRepository;

  public Board findById(String boardId) {
    return boardRepository.findById(UUID.fromString(boardId))
      .orElseThrow(() -> new GeneralException(ErrorStatus.KEY_NOT_EXIST, "존재하지 않는 게시판 ID입니다."));
  }
}
