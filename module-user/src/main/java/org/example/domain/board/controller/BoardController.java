package org.example.domain.board.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.board.enums.BoardCategory;
import org.example.domain.board.response.ListBoardResponse;
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


    /*
      @GetMapping("/temp")
  @Operation(summary = "자율 스터디 목록 조회")
  public ApiResponse<ListStudyResponse> getTempStudyList() {
    return ApiResponse.onSuccess(studyService.getTempStudyList());
  }

  @GetMapping("/{study-id}")
  @LimitRegularStudyMember
  @Operation(summary = "자율 스터디 상세 조회")
  public ApiResponse<DetailTempStudyResponse> getTempStudy(
    @PathVariable("study-id") Long studyId
  ) {
    return ApiResponse.onSuccess(studyService.getTempStudy(studyId));
  }


    */

    @GetMapping
    @Operation(summary = "카테고리별 목록 조회")
    public ApiResponse<ListBoardResponse> getBoardList(@RequestParam BoardCategory boardCategory) {
        return ApiResponse.onSuccess(BoardService.getBoardList(boardCategory));
    }

}
