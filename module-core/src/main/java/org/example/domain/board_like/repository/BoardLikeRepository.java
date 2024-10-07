package org.example.domain.board_like.repository;

import org.example.domain.board_like.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {

}
