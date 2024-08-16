package org.example.domain.board.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.Board.Board;
import org.example.domain.Board.repository.BoardRepository;
import org.example.domain.board.controller.request.CreateBoardRequest;
import org.example.domain.board_file.BoardFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateBoardService {

  private final BoardRepository boardRepository;

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
}
