package org.example.domain.board_file.service;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.board.enums.BoardCategory;
import org.example.domain.board_file.BoardFile;
import org.example.domain.board_file.repository.BoardFileRepository;
import org.example.domain.s3_file.service.CoreCreateS3FileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateBoardFileService {

  private final CoreCreateS3FileService coreCreateS3FileService;
  private final BoardFileRepository boardFileRepository;

  /**
   * 게시글 첨부파일 삭제
   */
  public void deleteBoardFile(String fileUrl) {
    BoardFile boardFile = boardFileRepository.findBoardFileByFileUrl(fileUrl)
      .orElseThrow(() -> new GeneralException(ErrorStatus.NOT_FOUND, "해당 첨부파일을 찾을 수 없습니다."));
    if (boardFile.getBoard().getCategory().equals(BoardCategory.NOTICE)) {
      throw new GeneralException(ErrorStatus.UNAUTHORIZED, "삭제할 수 없는 첨부파일입니다.");
    }
    boardFileRepository.delete(boardFile);
    coreCreateS3FileService.deleteS3File(fileUrl);
  }
}
