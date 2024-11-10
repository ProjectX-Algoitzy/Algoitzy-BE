package org.example.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.board.controller.request.CreateBoardRequest;
import org.example.domain.board.controller.request.SearchBoardRequest;
import org.example.domain.board.controller.request.UpdateBoardRequest;
import org.example.domain.board.controller.response.DetailBoardResponse;
import org.example.domain.board.controller.response.DetailDraftBoardResponse;
import org.example.domain.board.controller.response.ListBoardCategoryResponse;
import org.example.domain.board.controller.response.ListBoardResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

  private final CreateBoardService createBoardService;
  private final ListBoardService listBoardService;
  private final DetailBoardService detailBoardService;

  /**
   * 게시글 카테고리 목록 조회
   */
  public ListBoardCategoryResponse getBoardCategoryList() {
    return listBoardService.getBoardCategoryList();
  }

  /**
   * 공지사항 게시글 생성
   */
  public long createBoard(CreateBoardRequest request) {
    return createBoardService.createBoard(request);
  }

  /**
   * 게시글 목록 조회
   */
  public ListBoardResponse getBoardList(SearchBoardRequest request) {
    return listBoardService.getBoardList(request);
  }

  /**
   * 게시글 상세 조회
   */
  public DetailBoardResponse getBoard(Long boardId) {
    return detailBoardService.getBoard(boardId);
  }

  /**
   * 임시저장 게시글 존재 여부 조회
   */
  public boolean checkDraftBoard() {
    return detailBoardService.checkDraftBoard();
  }

  /**
   * 임시저장 게시글 조회
   */
  public DetailDraftBoardResponse getDraftBoard() {
    return detailBoardService.getDraftBoard();
  }

  /**
   * 공지사항 게시글 수정
   */
  public void updateBoard(Long boardId, UpdateBoardRequest request) {
    createBoardService.updateBoard(boardId, request);
  }

  /**
   * 게시글 삭제
   */
  public void deleteBoard(Long boardId) {
    createBoardService.deleteBoard(boardId);
  }

  /**
   * 공지사항 게시글 고정 여부 변경
   */
  public void updateBoardFix(Long boardId) {
    createBoardService.updateBoardFix(boardId);
  }
}
