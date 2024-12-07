package org.example.domain.board_file.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardFileService {

  private final CreateBoardFileService createBoardFileService;

  /**
   * 게시글 첨부파일 삭제
   */
  public void deleteBoardFile(String fileUrl) {
    createBoardFileService.deleteBoardFile(fileUrl);
  }
}
