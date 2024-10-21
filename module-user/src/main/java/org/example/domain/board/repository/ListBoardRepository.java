package org.example.domain.board.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.board.enums.BoardCategory;
import org.example.domain.board.response.ListBoardDto;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ListBoardRepository {

    private final JPAQueryFactory queryFactory;

    /*
    * 게시판 목록 조회
    * */
    public List<ListBoardDto> getBoardList(BoardCategory boardCategory) {

    }

}
