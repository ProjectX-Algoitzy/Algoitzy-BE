package org.example.domain.board.service;

import java.util.List;
import java.util.Optional;
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
   * 임시저장 게시글 존재 여부 조회
   */
  public boolean checkDraftBoard() {
    return detailBoardRepository.getDraftBoard() != null;
  }

  /**
   * 임시저장 게시글 조회
   */
  public DetailDraftBoardResponse getDraftBoard() {
    DetailDraftBoardResponse board = Optional.ofNullable(detailBoardRepository.getDraftBoard())
      .orElseThrow(() -> new GeneralException(ErrorStatus.NOT_FOUND, "임시저장된 게시글이 존재하지 않습니다."));
    List<ListBoardFileDto> boardFileList = listBoardFileRepository.getBoardFileList(board.getBoardId());

    return board.toBuilder()
      .boardFileList(boardFileList)
      .build();
  }
}
