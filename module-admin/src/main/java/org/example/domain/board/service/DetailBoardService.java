package org.example.domain.board.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.board.controller.response.DetailBoardResponse;
import org.example.domain.board.controller.response.DetailDraftBoardResponse;
import org.example.domain.board.repository.DetailBoardRepository;
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

  /**
   * 임시저장 게시글 상세 조회
   */
  public DetailDraftBoardResponse getDraftBoard(Long boardId) {
    DetailDraftBoardResponse board = detailBoardRepository.getDraftBoard(boardId);
    if (board == null) throw new GeneralException(ErrorStatus.NOTICE_NOT_FOUND, "해당 ID의 임시저장 게시글이 존재하지 않습니다.");
    board.updateCategory(board.getCategory());

    List<ListBoardFileDto> boardFileList = listBoardFileRepository.getBoardFileList(board.getBoardId());

    return board.toBuilder()
      .boardFileList(boardFileList)
      .build();
  }
}
