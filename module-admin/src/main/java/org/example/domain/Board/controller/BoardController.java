package org.example.domain.Board.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.Board.controller.request.CreateBoardRequest;
import org.example.domain.Board.controller.request.SearchBoardRequest;
import org.example.domain.Board.controller.response.DetailBoardResponse;
import org.example.domain.Board.controller.response.ListBoardResponse;
import org.example.domain.Board.service.BoardService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
@Tag(name = "BoardController", description = "[ADMIN] 커뮤니티 관련 API")
public class BoardController {

  private final BoardService boardService;

  @PostMapping
  @Operation(summary = "공지사항 게시글 생성")
  public ApiResponse<Void> createBoard(CreateBoardRequest request) {
    boardService.createBoard(request);
    return ApiResponse.onCreate();
  }

  @GetMapping
  @Operation(summary = "게시글 목록 조회")
  public ApiResponse<ListBoardResponse> getBoardList(
    @ParameterObject @ModelAttribute @Valid SearchBoardRequest request) {
    return ApiResponse.onSuccess(boardService.getBoardList(request));
  }

  @GetMapping("/{board-id}")
  @Operation(summary = "게시글 상세 조회")
  public ApiResponse<DetailBoardResponse> getBoard(
    @PathVariable("board-id") Long boardId) {
    return ApiResponse.onSuccess(boardService.getBoard(boardId));
  }

}
