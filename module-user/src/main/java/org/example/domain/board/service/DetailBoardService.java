package org.example.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.Board.Board;
import org.example.domain.Board.service.CoreBoardService;
import org.example.domain.board.controller.response.DetailBoardResponse;
import org.example.domain.board.repository.DetailBoardRepository;
import org.example.domain.board_file.BoardFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetailBoardService {

  private final CoreBoardService coreBoardService;
  private final DetailBoardRepository detailBoardRepository;

  /**
   * 게시판 상세 조회
   */
  public DetailBoardResponse getBoard(Long boardId) {
    Board board = coreBoardService.findById(boardId);
    DetailBoardResponse response = detailBoardRepository.getBoard(boardId);
    response.setFileUrlList(board.getFileList().stream().map(BoardFile::getFileUrl).toList());
    response.encryptCreatedName();

    return response;
  }
}
