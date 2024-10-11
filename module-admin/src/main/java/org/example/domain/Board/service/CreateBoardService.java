package org.example.domain.Board.service;

import com.vane.badwordfiltering.BadWordFiltering;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.Board.controller.request.CreateBoardRequest;
import org.example.domain.Board.controller.request.UpdateBoardRequest;
import org.example.domain.board.Board;
import org.example.domain.board.enums.Category;
import org.example.domain.board.repository.BoardRepository;
import org.example.domain.board.service.CoreBoardService;
import org.example.domain.board_file.BoardFile;
import org.example.domain.member.service.CoreMemberService;
import org.example.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateBoardService {

  private final CoreBoardService coreBoardService;
  private final CoreMemberService coreMemberService;
  private final BoardRepository boardRepository;
  private final BadWordFiltering badWordFiltering;

  /**
   * 공지사항 게시글 생성
   */
  public void createBoard(CreateBoardRequest request) {
    if (badWordFiltering.blankCheck(request.title()) ||
      badWordFiltering.blankCheck(request.content())) {
      throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "제목/내용에 욕설을 포함할 수 없습니다.");
    }

    // 게시글 생성
    Board board = Board.builder()
      .title(request.title())
      .content(request.content())
      .category(Category.NOTICE)
      .member(coreMemberService.findByEmail(SecurityUtils.getCurrentMemberEmail()))
      .saveYn(true)
      .fixYn(request.fixYn())
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

    boardRepository.save(board);
  }

  /**
   * 공지사항 게시글 수정
   */
  public void updateBoard(Long boardId, UpdateBoardRequest request) {
    Board board = coreBoardService.findById(boardId);
    if (!board.getCategory().equals(Category.NOTICE)) {
      throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "공지사항 외 게시글은 수정할 수 없습니다.");
    }

    board.updateNoticeBoard(
      request.title(),
      request.content(),
      request.fixYn()
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
}
