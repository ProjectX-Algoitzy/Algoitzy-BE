package org.example.domain.Board.controller;

import com.vane.badwordfiltering.BadWordFiltering;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.Board.controller.request.CreateBoardRequest;
import org.example.domain.Board.service.BoardService;
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

}
