package org.example.domain.Board.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.Board.controller.request.CreateBoardRequest;
import org.example.domain.Board.controller.request.SearchBoardRequest;
import org.example.domain.Board.controller.request.UpdateBoardRequest;
import org.example.domain.Board.controller.response.DetailBoardResponse;
import org.example.domain.Board.controller.response.ListBoardResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

  private final CreateBoardService createBoardService;
  private final ListBoardService listBoardService;
  private final DetailBoardService detailBoardService;

  /**
   * 공지사항 게시글 생성
   */
  public void createBoard(CreateBoardRequest request) {
    createBoardService.createBoard(request);
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
