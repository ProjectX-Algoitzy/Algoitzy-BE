package org.example.domain.board.service;

import com.vane.badwordfiltering.BadWordFiltering;
import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.board.Board;
import org.example.domain.board.controller.request.CreateBoardRequest;
import org.example.domain.board.repository.BoardRepository;
import org.example.domain.board_file.BoardFile;
import org.example.domain.member.service.CoreMemberService;
import org.example.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateBoardService {

    private final BoardRepository boardRepository;
    private final BadWordFiltering badWordFiltering;
    private final CoreMemberService coreMemberService;

    /*
    * 게시글 생성
    * */
    public long createBoard(CreateBoardRequest request) {
        if (badWordFiltering.blankCheck(request.title()) ||
            badWordFiltering.blankCheck(request.content())) {
            throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "제목/내용에 욕설을 포함할 수 없습니다.");
        }

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
}
