package org.example.domain.board.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.board.controller.request.CreateBoardRequest;
import org.example.domain.board.controller.request.SearchBoardRequest;
import org.example.domain.board.controller.request.UpdateBoardRequest;
import org.example.domain.reply.controller.request.SearchReplyRequest;
import org.example.domain.board.controller.response.DetailBoardResponse;
import org.example.domain.board.controller.response.ListBoardResponse;
import org.example.domain.reply.controller.response.ListReplyResponse;
import org.example.domain.board.controller.response.ListBoardCategoryResponse;
import org.example.domain.board.service.BoardService;
import org.example.domain.reply.service.ReplyService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
@Tag(name = "BoardController", description = "[USER] 커뮤니티 관련 API")
public class BoardController {

    private final BoardService boardService;
    private final ReplyService replyService;

    @GetMapping("/category")
    @Operation(summary = "게시글 카테고리 목록 조회")
    public ApiResponse<ListBoardCategoryResponse> getBoardList() {
        return ApiResponse.onSuccess(boardService.getBoardCategoryList());
    }

    @GetMapping
    @Operation(summary = "게시글 목록 조회")
    public ApiResponse<ListBoardResponse> getBoardList(@ParameterObject @ModelAttribute @Valid
        SearchBoardRequest request) {
        return ApiResponse.onSuccess(boardService.getBoardList(request));
    }

    @GetMapping("/{board-id}")
    @Operation(summary = "게시글 상세 조회")
    public ApiResponse<DetailBoardResponse> getDetailBoard(@PathVariable("board-id") Long boardId) {
        return ApiResponse.onSuccess(boardService.getDetailBoard(boardId));
    }

    @PostMapping
    @Operation(summary = "게시글 생성")
    public ApiResponse<Long> createBoard(
      @RequestBody @Valid CreateBoardRequest request) {
        return ApiResponse.onSuccess(boardService.createBoard(request));
    }

    @GetMapping("/{board-id}/reply")
    @Operation(summary = "게시글 댓글 목록 조회")
    public ApiResponse<ListReplyResponse> getReplyList(
        @PathVariable("board-id") Long boardId,
        @ParameterObject @ModelAttribute @Valid SearchReplyRequest request) {
        return ApiResponse.onSuccess(replyService.getReplyList(boardId, request));
    }

    @GetMapping("/draft")
    @Operation(summary = "임시저장 게시글 조회")
    public ApiResponse<ListBoardResponse> getDraftBoardList() {
        return ApiResponse.onSuccess(boardService.getDraftBoardList());
    }

    @PatchMapping("/{board-id}")
    @Operation(summary = "게시글 수정")
    public ApiResponse<Void> updateBoard(
      @PathVariable("board-id") long boardId,
      @RequestBody @Valid UpdateBoardRequest request) {
        boardService.updateBoard(boardId, request);
        return ApiResponse.onSuccess();
    }

    @DeleteMapping("{board-id}")
    @Operation(summary = "게시글 삭제")
    public ApiResponse<Void> deleteBoard(@PathVariable("board-id") long boardId) {
        boardService.deleteBoard(boardId);
        return ApiResponse.onSuccess();
    }

}
