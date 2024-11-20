package org.example.domain.board.service;

import lombok.RequiredArgsConstructor;
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
    private final CoreMemberService coreMemberService;

    /*
    * 게시글 생성
    * */
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
}
