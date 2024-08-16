package org.example.domain.Board.repository;

import java.util.UUID;
import org.example.domain.Board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, UUID> {

}
