package org.example.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.board.Board;
import org.example.domain.board.repository.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CoreBoardService {

  private final BoardRepository boardRepository;

  public Board findById(Long id) {
    return boardRepository.findById(id)
      .orElseThrow(() -> new GeneralException(ErrorStatus.KEY_NOT_EXIST, "존재하지 않는 게시글 ID입니다."));
  }

}
