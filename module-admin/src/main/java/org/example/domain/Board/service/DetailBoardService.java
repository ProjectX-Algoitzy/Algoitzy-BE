package org.example.domain.Board.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.Board.controller.response.DetailBoardResponse;
import org.example.domain.Board.repository.DetailBoardRepository;
import org.example.domain.board_file.controller.response.ListBoardFileDto;
import org.example.domain.board_file.repository.ListBoardFileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetailBoardService {

  private final DetailBoardRepository detailBoardRepository;
  private final ListBoardFileRepository listBoardFileRepository;

  /**
   * 게시글 상세 조회
   */
  public DetailBoardResponse getBoard(Long boardId) {
    DetailBoardResponse board = detailBoardRepository.getBoard(boardId);
    board.updateCategory(board.getCategory());

    List<ListBoardFileDto> boardFileList = listBoardFileRepository.getBoardFileList(board.getBoardId());

    return board.toBuilder()
      .boardFileList(boardFileList)
      .build();
  }
}
