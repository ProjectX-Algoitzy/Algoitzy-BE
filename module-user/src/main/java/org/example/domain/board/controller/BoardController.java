package org.example.domain.board.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.board.controller.request.CreateBoardRequest;
import org.example.domain.board.controller.request.SearchBoardRequest;
import org.example.domain.board.controller.request.UpdateBoardRequest;
import org.example.domain.board.controller.response.DetailBoardResponse;
import org.example.domain.board.controller.response.ListBoardResponse;
import org.example.domain.board.service.BoardService;
import org.example.domain.comment.controller.request.CreateCommentRequest;
import org.example.domain.comment.controller.request.SearchCommentRequest;
import org.example.domain.comment.controller.response.ListCommentResponse;
import org.example.domain.comment.service.CommentService;
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
@Tag(name = "BoardController", description = "[USER] 게시판 관련 API")
public class BoardController {

  private final BoardService boardService;
  private final CommentService commentService;

  @PostMapping()
  @Operation(summary = "게시판 생성")
  public ApiResponse<Void> createBoard(@RequestBody @Valid CreateBoardRequest request) {
    boardService.createBoard(request);
    return ApiResponse.onCreate();
  }

  @GetMapping()
  @Operation(summary = "게시판 목록 조회")
  public ApiResponse<ListBoardResponse> getBoardList(
    @ParameterObject @ModelAttribute @Valid SearchBoardRequest request) {
    return ApiResponse.onSuccess(boardService.getBoardList(request));
  }

  @GetMapping("/{board-id}")
  @Operation(summary = "게시판 상세 조회")
  public ApiResponse<DetailBoardResponse> getBoard(@PathVariable("board-id") Long boardId) {
    return ApiResponse.onSuccess(boardService.getBoard(boardId));
  }

  @PatchMapping("/{board-id}")
  @Operation(summary = "게시판 수정")
  public ApiResponse<Void> updateBoard(
    @PathVariable("board-id") Long boardId,
    @RequestBody @Valid UpdateBoardRequest request) {
    boardService.updateBoard(boardId, request);
    return ApiResponse.onCreate();
  }

  @DeleteMapping("/{board-id}")
  @Operation(summary = "게시판 삭제")
  public ApiResponse<Void> deleteBoard(@PathVariable("board-id") Long boardId) {
    boardService.deleteBoard(boardId);
    return ApiResponse.onSuccess();
  }

  @PostMapping("/{board-id}/comment")
  @Operation(summary = "게시판 댓글 생성")
  public ApiResponse<Void> createComment(
    @PathVariable("board-id") Long boardId,
    @RequestBody @Valid CreateCommentRequest request) {
    commentService.createComment(boardId, request);
    return ApiResponse.onCreate();
  }

  @GetMapping("/{board-id}/comment")
  @Operation(summary = "댓글 목록 조회")
  public ApiResponse<ListCommentResponse> getCommentList(
    @PathVariable("board-id") Long boardId,
    @ParameterObject @ModelAttribute @Valid SearchCommentRequest request) {
    return ApiResponse.onSuccess(commentService.getCommentList(boardId, request));
  }
}
