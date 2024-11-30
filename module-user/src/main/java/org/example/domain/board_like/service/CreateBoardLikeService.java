package org.example.domain.board_like.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.board.Board;
import org.example.domain.board.repository.BoardRepository;
import org.example.domain.board.service.CoreBoardService;
import org.example.domain.board_like.BoardLike;
import org.example.domain.board_like.repository.BoardLikeRepository;
import org.example.domain.member.Member;
import org.example.domain.member.service.CoreMemberService;
import org.example.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateBoardLikeService {

    private final CoreBoardService coreBoardService;
    private final CoreMemberService coreMemberService;
    private final BoardLikeRepository boardLikeRepository;
    private final BoardRepository boardRepository;

    public void createBoardLike(long boardId) {
        Board board = coreBoardService.findById(boardId);
        Member member = coreMemberService.findByEmail(SecurityUtils.getCurrentMemberEmail());

        // 사용자가 해당 게시물에 좋아요를 눌렀는지 확인
        BoardLike isBoardLiked = boardLikeRepository.findByMemberAndBoard(member, board).orElse(null);

        if (isBoardLiked != null) {
            // 이미 좋아요가 눌러져 있으면 삭제
            boardLikeRepository.delete(isBoardLiked);
            board.deleteBoardLike(isBoardLiked);
        } else {
            // 좋아요가 눌러져 있지 않으면 생성
            BoardLike boardLike = BoardLike.builder()
                .member(member)
                .board(board)
                .build();

            boardLikeRepository.save(boardLike);
            board.createBoardLlike(boardLike);
        }
        boardRepository.save(board);
    }
}
