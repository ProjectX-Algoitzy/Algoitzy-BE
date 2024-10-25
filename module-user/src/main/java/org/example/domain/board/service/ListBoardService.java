package org.example.domain.board.service;

import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.example.domain.board.enums.BoardCategory;
import org.example.domain.board.repository.ListBoardRepository;
import org.example.domain.board.controller.response.ListBoardCategoryDto;
import org.example.domain.board.controller.response.ListBoardCategoryResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListBoardService {

    private final ListBoardRepository listBoardRepository;

    /*
    * 게시글 카테고리 목록 조회
    * */
    public ListBoardCategoryResponse getBoardCategoryList() {
        List<ListBoardCategoryDto> categoryList = Stream.of(BoardCategory.values())
            .map(category -> ListBoardCategoryDto.builder()
                .code(category.name())
                .name(category.getName())
                .build())
            .toList();

        return ListBoardCategoryResponse.builder()
            .categoryList(categoryList)
            .build();
    }

}
