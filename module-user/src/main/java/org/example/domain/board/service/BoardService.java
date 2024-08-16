package org.example.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.board.controller.request.CreateBoardRequest;
import org.example.domain.board.controller.request.SearchBoardRequest;
import org.example.domain.board.controller.request.UpdateBoardRequest;
import org.example.domain.board.controller.response.DetailBoardResponse;
import org.example.domain.board.controller.response.ListBoardResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

  private final CreateBoardService createBoardService;
  private final ListBoardService listBoardService;
  private final DetailBoardService detailBoardService;

  /**
   * 게시판 생성
   */
  public void createBoard(CreateBoardRequest request) {
    createBoardService.createBoard(request);
  }

  /**
   * 게시판 목록 조회
   */
  public ListBoardResponse getBoardList(SearchBoardRequest request) {
    return listBoardService.getBoardList(request);
  }

  /**
   * 게시판 상세 조회
   */
  public DetailBoardResponse getBoard(Long boardId) {
    return detailBoardService.getBoard(boardId);
  }

  /**
   * 게시판 수정
   */
  public void updateBoard(Long boardId, UpdateBoardRequest request) {
    createBoardService.updateBoard(boardId, request);
  }

  /**
   * 게시판 삭제
   */
  public void deleteBoard(Long boardId) {
    createBoardService.deleteBoard(boardId);
  }
}
