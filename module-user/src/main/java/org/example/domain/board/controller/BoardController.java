package org.example.domain.board.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.board.controller.request.CreateBoardRequest;
import org.example.domain.board.controller.request.SearchBoardRequest;
import org.example.domain.board.controller.response.ListBoardResponse;
import org.example.domain.board.service.BoardService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    return ApiResponse.onCreate(boardService.getBoardList(request));
  }

}
