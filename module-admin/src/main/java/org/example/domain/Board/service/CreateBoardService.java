package org.example.domain.Board.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.domain.Board.controller.request.CreateBoardRequest;
import org.example.domain.board.Board;
import org.example.domain.board.enums.Category;
import org.example.domain.board.repository.BoardRepository;
import org.example.domain.board_file.BoardFile;
import org.example.domain.board_file.repository.BoardFileRepository;
import org.example.domain.member.service.CoreMemberService;
import org.example.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateBoardService {

  private final CoreMemberService coreMemberService;
  private final BoardRepository boardRepository;
  private final BoardFileRepository boardFileRepository;

  /**
   * 공지사항 게시글 생성
   */
  public void createBoard(CreateBoardRequest request) {
    // 게시글 저장
    Board board = boardRepository.save(
      Board.builder()
        .title(request.title())
        .content(request.content())
        .category(Category.NOTICE)
        .member(coreMemberService.findByEmail(SecurityUtils.getCurrentMemberEmail()))
        .saveYn(true)
        .fixYn(request.fixYn())
        .build()
    );

    // 첨부파일 저장
    if (ObjectUtils.isEmpty(request.fileUrlList())) return;
    boardFileRepository.saveAll(
      request.fileUrlList().stream()
        .map(fileUrl ->
          BoardFile.builder()
            .board(board)
            .fileUrl(fileUrl)
            .build()
        ).toList()
    );
  }
}
