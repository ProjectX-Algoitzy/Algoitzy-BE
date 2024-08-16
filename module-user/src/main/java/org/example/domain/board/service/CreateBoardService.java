package org.example.domain.board.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.Board.Board;
import org.example.domain.Board.repository.BoardRepository;
import org.example.domain.Board.service.CoreBoardService;
import org.example.domain.board.controller.request.CreateBoardRequest;
import org.example.domain.board.controller.request.UpdateBoardRequest;
import org.example.domain.board_file.BoardFile;
import org.example.domain.s3_file.service.CoreCreateS3FileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateBoardService {

  private final CoreCreateS3FileService coreCreateS3FileService;
  private final CoreBoardService coreBoardService;
  private final BoardRepository boardRepository;

  /**
   * 게시판 생성
   */
  public void createBoard(CreateBoardRequest request) {
    Board board = boardRepository.save(
      Board.builder()
        .title(request.title())
        .content(request.content())
        .build()
    );

    List<BoardFile> fileList = new ArrayList<>();
    if (!request.fileUrlList().isEmpty()) {
      for (String fileUrl : request.fileUrlList()) {
        fileList.add(
          BoardFile.builder()
            .board(board)
            .fileUrl(fileUrl)
            .build()
        );
      }
    }
    board.setFileList(fileList);
  }

  /**
   * 게시판 수정
   */
  public void updateBoard(String boardId, UpdateBoardRequest request) {
    Board board = coreBoardService.findById(boardId);
    board.update(request.title(), request.content());

    // 기존 파일 S3 삭제
    for (BoardFile boardFile : board.getFileList()) {
      coreCreateS3FileService.deleteS3File(boardFile.getFileUrl());
    }
    board.getFileList().clear();
    if (!request.fileUrlList().isEmpty()) {
      for (String fileUrl : request.fileUrlList()) {
        board.getFileList().add(
          BoardFile.builder()
            .board(board)
            .fileUrl(fileUrl)
            .build()
        );
      }
    }
  }

  /**
   * 게시판 삭제
   */
  public void deleteBoard(String boardId) {
    Board board = coreBoardService.findById(boardId);
    boardRepository.delete(board);
  }
}
