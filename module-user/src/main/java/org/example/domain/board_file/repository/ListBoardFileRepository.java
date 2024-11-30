package org.example.domain.board_file.repository;

import static org.example.domain.board_file.QBoardFile.boardFile;
import static org.example.domain.s3_file.QS3File.s3File;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.board_file.controller.ListBoardFileDto;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ListBoardFileRepository {

    private final JPAQueryFactory queryFactory;

    public List<ListBoardFileDto> getBoardFileList(Long boardId) {
        return queryFactory
            .select(
                Projections.fields(
                    ListBoardFileDto.class,
                    s3File.originalName,
                    boardFile.fileUrl
                )
            )
            .from(boardFile)
            .innerJoin(s3File).on(boardFile.fileUrl.eq(s3File.fileUrl))
            .where(boardFile.board.id.eq(boardId))
            .fetch();
    }
}
