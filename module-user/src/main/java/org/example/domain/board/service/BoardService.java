package org.example.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.board.controller.request.CreateBoardRequest;
import org.example.domain.board.controller.request.SearchBoardRequest;
import org.example.domain.board.controller.request.UpdateBoardRequest;
import org.example.domain.board.controller.response.DetailBoardResponse;
import org.example.domain.board.controller.response.ListBoardCategoryResponse;
import org.example.domain.board.controller.response.ListBoardResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final ListBoardService listBoardService;
    private final DetailBoardService detailBoardService;
    private final CreateBoardService createBoardService;

    /**
     * 게시글 카테고리 목록 조회
     */
    public ListBoardCategoryResponse getBoardCategoryList() {
        return listBoardService.getBoardCategoryList();
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
    public DetailBoardResponse getDetailBoard(Long boardId) {
        return detailBoardService.getDetailBoard(boardId);
    }

    /**
     * 임시저장 게시글 조회
     */
    public ListBoardResponse getDraftBoardList() {
        return listBoardService.getDraftBoardList();
    }

    /**
     * 게시글 생성
     */
    public Long createBoard(CreateBoardRequest request) {
        return createBoardService.createBoard(request);
    }

    /**
     * 게시글 수정
     */
    public void updateBoard(long boardId, UpdateBoardRequest request) {
        createBoardService.updateBoard(boardId, request);
    }

    /**
     * 게시글 삭제
     */
    public void deleteBoard(long boardId) {
        createBoardService.deleteBoard(boardId);
    }
}
