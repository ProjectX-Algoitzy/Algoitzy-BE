package org.example.domain.board.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.board.controller.response.DetailBoardResponse;
import org.example.domain.board.repository.DetailBoardRepository;
import org.example.domain.board_file.controller.ListBoardFileDto;
import org.example.domain.board_file.repository.ListBoardFileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetailBoardService {

    private final DetailBoardRepository detailBoardRepository;
    private final ListBoardFileRepository listBoardFileRepository;

    /*
    * 게시글 상세 조회
    * */
    public DetailBoardResponse getDetailBoard(Long boardId) {
        DetailBoardResponse board = detailBoardRepository.getDetailBoard(boardId);
        board.updateCategory(board.getCategory());

        List<ListBoardFileDto> boardFileList = listBoardFileRepository.getBoardFileList(board.getBoardId());

        return board.toBuilder()
            .boardFileList(boardFileList)
            .build();
    }

}
