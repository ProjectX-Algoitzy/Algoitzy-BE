package org.example.domain.board.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.board.Board;
import org.example.domain.board.controller.request.CreateBoardRequest;
import org.example.domain.board.controller.request.UpdateBoardRequest;
import org.example.domain.board.enums.BoardCategory;
import org.example.domain.board.repository.BoardRepository;
import org.example.domain.board_file.BoardFile;
import org.example.domain.member.Member;
import org.example.domain.member.service.CoreMemberService;
import org.example.domain.s3_file.service.CoreCreateS3FileService;
import org.example.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateBoardService {

  private final BoardRepository boardRepository;
  private final CoreMemberService coreMemberService;
  private final CoreBoardService coreBoardService;
  private final CoreCreateS3FileService coreCreateS3FileService;

  /**
   * 게시글 생성
   */
  public long createBoard(CreateBoardRequest request) {

    // 게시글 생성
    Board board = Board.builder()
      .title(request.title())
      .content(request.content())
      .category(request.category())
      .member(coreMemberService.findByEmail(SecurityUtils.getCurrentMemberEmail()))
      .saveYn(true)
      .build();

    // 첨부파일 생성
    if (!ObjectUtils.isEmpty(request.fileUrlList())) {
      board.setBoardFileList(
        request.fileUrlList().stream()
          .map(fileUrl ->
            BoardFile.builder()
              .board(board)
              .fileUrl(fileUrl)
              .build()
          ).toList()
      );
    }

    Board newBoard = boardRepository.save(board);
    return newBoard.getId();
  }

  /**
   * 게시글 수정
   */
  public void updateBoard(long boardId, UpdateBoardRequest request) {
    Board board = coreBoardService.findById(boardId);
    if (!board.getMember().equals(coreMemberService.findByEmail(SecurityUtils.getCurrentMemberEmail()))) {
      throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "자신이 남긴 게시글 이외에는 수정할 수 없습니다.");
    }

    board.updateBoard(
      request.title(),
      request.content()
    );

    List<BoardFile> boardFileList = board.getBoardFileList();
    boardFileList.clear();

    if (!ObjectUtils.isEmpty(request.fileUrlList())) {
      boardFileList.addAll(
        request.fileUrlList().stream()
          .map(fileUrl ->
            BoardFile.builder()
              .board(board)
              .fileUrl(fileUrl)
              .build()
          ).toList());
    }
  }

  /**
   * 게시글 삭제
   */
  public void deleteBoard(long boardId) {
    Board board = coreBoardService.findById(boardId);
    board.getBoardFileList()
      .forEach(boardFile -> coreCreateS3FileService.deleteS3File(boardFile.getFileUrl()));

    if (!board.getMember().equals(coreMemberService.findByEmail(SecurityUtils.getCurrentMemberEmail()))) {
      throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "자신이 남긴 게시글 이외에는 삭제할 수 없습니다.");
    }

    boardRepository.deleteById(boardId);
    board.delete();
  }


}
