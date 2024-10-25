package org.example.domain.board.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.board.enums.BoardCategory;
import org.example.domain.board.controller.response.ListBoardCategoryResponse;
import org.example.domain.board.service.BoardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
@Tag(name = "BoardController", description = "[USER] 커뮤니티 관련 API")
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    @Operation(summary = "게시글 카테고리 목록 조회")
    public ApiResponse<ListBoardCategoryResponse> getBoardList(@RequestParam BoardCategory boardCategory) {
        return ApiResponse.onSuccess(boardService.getBoardCategoryList());
    }

}
