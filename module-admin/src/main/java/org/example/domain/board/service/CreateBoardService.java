package org.example.domain.board.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.board.controller.request.CreateBoardRequest;
import org.example.domain.board.controller.request.UpdateBoardRequest;
import org.example.domain.board.Board;
import org.example.domain.board.enums.BoardCategory;
import org.example.domain.board.repository.BoardRepository;
import org.example.domain.board_file.BoardFile;
import org.example.domain.member.service.CoreMemberService;
import org.example.domain.s3_file.service.CoreCreateS3FileService;
import org.example.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateBoardService {

  private final CoreBoardService coreBoardService;
  private final CoreMemberService coreMemberService;
  private final CoreCreateS3FileService coreCreateS3FileService;
  private final BoardRepository boardRepository;

  /**
   * 공지사항 게시글 생성
   */
  public long createBoard(CreateBoardRequest request) {
    if (!StringUtils.hasText(request.title()) || !StringUtils.hasText(request.content()))
      throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "제목 또는 내용이 비어있습니다.");

    // 게시글 생성
    Board board = Board.builder()
      .title(request.title())
      .content(request.content())
      .category(BoardCategory.NOTICE)
      .member(coreMemberService.findByEmail(SecurityUtils.getCurrentMemberEmail()))
      .saveYn(request.saveYn())
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
          ).toList());
    }

    Board newBoard = boardRepository.save(board);
    return newBoard.getId();
  }

  /**
   * 공지사항 게시글 수정
   */
  public void updateBoard(Long boardId, UpdateBoardRequest request) {
    if (!StringUtils.hasText(request.title()) || !StringUtils.hasText(request.content()))
      throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "제목 또는 내용이 비어있습니다.");

    Board board = coreBoardService.findById(boardId);
    if (!board.getCategory().equals(BoardCategory.NOTICE)) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "공지사항 외 게시글은 수정할 수 없습니다.");
    }

    if (board.getSaveYn() && !request.saveYn()) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "등록된 게시글은 임시 저장할 수 없습니다.");
    }

    board.updateBoard(
      BoardCategory.NOTICE,
      request.title(),
      request.content(),
      request.saveYn()
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
  public void deleteBoard(Long boardId) {
    Board board = coreBoardService.findById(boardId);
    board.getBoardFileList()
      .forEach(boardFile -> coreCreateS3FileService.deleteS3File(boardFile.getFileUrl()));

    if (board.getCategory().equals(BoardCategory.NOTICE)) {
      boardRepository.deleteById(boardId);
      return;
    }
    board.delete();
  }

  /**
   * 공지사항 게시글 고정 여부 변경
   */
  public void updateBoardFix(Long boardId) {
    Board board = coreBoardService.findById(boardId);
    if (!board.getCategory().equals(BoardCategory.NOTICE)) {
      throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "공지사항 외 게시글은 고정할 수 없습니다.");
    }

    board.updateFixYn();
  }
}
