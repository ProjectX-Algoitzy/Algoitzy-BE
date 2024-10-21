package org.example.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.board.enums.BoardCategory;
import org.example.domain.board.response.ListBoardResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    /*
    * 카테고리별 게시판 목록 조회
    * */
    public ListBoardResponse getBoardList(BoardCategory boardCategory) {

    }

}
