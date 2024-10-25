package org.example.domain.board.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.board.controller.request.SearchBoardRequest;
import org.example.domain.board.controller.response.ListBoardResponse;
import org.example.domain.board.enums.BoardCategory;
import org.example.domain.board.controller.response.ListBoardCategoryResponse;
import org.example.domain.board.service.BoardService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
@Tag(name = "BoardController", description = "[USER] 커뮤니티 관련 API")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/category")
    @Operation(summary = "게시글 카테고리 목록 조회")
    public ApiResponse<ListBoardCategoryResponse> getBoardList(@RequestParam BoardCategory boardCategory) {
        return ApiResponse.onSuccess(boardService.getBoardCategoryList());
    }

    @GetMapping
    @Operation(summary = "게시글 목록 조회")
    public ApiResponse<ListBoardResponse> getBoardList(@ParameterObject @ModelAttribute @Valid
        SearchBoardRequest request) {
        return ApiResponse.onSuccess(boardService.getBoardList(request));
    }
}
