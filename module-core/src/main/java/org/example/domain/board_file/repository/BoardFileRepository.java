package org.example.domain.board_file.repository;

import java.util.Optional;
import org.example.domain.board_file.BoardFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardFileRepository extends JpaRepository<BoardFile, Long> {

  Optional<BoardFile> findBoardFileByFileUrl(String fileUrl);

}
