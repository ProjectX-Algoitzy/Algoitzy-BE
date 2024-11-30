package org.example.domain.board_like.repository;

import java.util.Optional;
import org.example.domain.board.Board;
import org.example.domain.board_like.BoardLike;
import org.example.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    Optional<BoardLike> findByMemberAndBoard(Member member, Board board);
}
