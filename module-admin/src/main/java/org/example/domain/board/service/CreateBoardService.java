package org.example.domain.board.service;

import com.vane.badwordfiltering.BadWordFiltering;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.board.controller.request.CreateBoardRequest;
import org.example.domain.board.controller.request.UpdateBoardRequest;
import org.example.domain.board.Board;
import org.example.domain.board.controller.response.DetailDraftBoardResponse;
import org.example.domain.board.enums.BoardCategory;
import org.example.domain.board.repository.BoardRepository;
import org.example.domain.board.repository.DetailBoardRepository;
import org.example.domain.board_file.BoardFile;
import org.example.domain.member.service.CoreMemberService;
import org.example.domain.s3_file.service.CoreCreateS3FileService;
import org.example.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateBoardService {

  private final CoreBoardService coreBoardService;
  private final CoreMemberService coreMemberService;
  private final CoreCreateS3FileService coreCreateS3FileService;
  private final DetailBoardRepository detailBoardRepository;
  private final BoardRepository boardRepository;
  private final BadWordFiltering badWordFiltering;

  /**
   * 공지사항 게시글 임시 생성/생성
   */
  public long createBoard(CreateBoardRequest request) {
    if (badWordFiltering.blankCheck(request.title()) ||
      badWordFiltering.blankCheck(request.content())) {
      throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "제목/내용에 욕설을 포함할 수 없습니다.");
    }

    // 기존 게시글 삭제
    if (request.boardId() != null) {
      if (coreBoardService.findById(request.boardId()).getSaveYn()) {
        throw new GeneralException(ErrorStatus.BAD_REQUEST, "최종 저장된 게시글입니다.");
      }
      deleteBoard(request.boardId());
    }

    // 기존 임시저장 게시글 삭제
    DetailDraftBoardResponse draftBoard = detailBoardRepository.getDraftBoard();
    if (draftBoard != null) {
      deleteBoard(draftBoard.getBoardId());
    }

    // 게시글 생성
    Board board = Board.builder()
      .title(request.title())
      .content(request.content())
      .category(BoardCategory.NOTICE)
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
          ).toList());
    }

    Board newBoard = boardRepository.save(board);
    return newBoard.getId();
  }

  /**
   * 공지사항 게시글 수정
   */
  public void updateBoard(Long boardId, UpdateBoardRequest request) {
    Board board = coreBoardService.findById(boardId);
    if (!board.getCategory().equals(BoardCategory.NOTICE)) {
      throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "공지사항 외 게시글은 수정할 수 없습니다.");
    }
    if (!board.getSaveYn()) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "최종 저장된 게시글만 수정할 수 있습니다.");
    }

    if (badWordFiltering.blankCheck(request.title()) ||
      badWordFiltering.blankCheck(request.content())) {
      throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "제목/내용에 욕설을 포함할 수 없습니다.");
    }

    board.updateNoticeBoard(
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
