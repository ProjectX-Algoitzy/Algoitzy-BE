package org.example.domain.board_file.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.board_file.service.BoardFileService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board-file")
@RequiredArgsConstructor
@Tag(name = "BoardFileController", description = "[ADMIN] 게시글 첨부파일 관련 API")
public class BoardFileController {

  private final BoardFileService boardFileService;

  @DeleteMapping
  @Operation(summary = "공지사항 게시글 첨부파일 삭제")
  public ApiResponse<Void> deleteBoardFile(@RequestParam String fileUrl) {
    boardFileService.deleteBoardFile(fileUrl);
    return ApiResponse.onSuccess();
  }
}
