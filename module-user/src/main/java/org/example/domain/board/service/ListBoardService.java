package org.example.domain.board.service;

import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.example.domain.board.controller.request.SearchBoardRequest;
import org.example.domain.board.controller.response.ListBoardDto;
import org.example.domain.board.controller.response.ListBoardResponse;
import org.example.domain.board.enums.BoardCategory;
import org.example.domain.board.repository.ListBoardRepository;
import org.example.domain.board.controller.response.ListBoardCategoryDto;
import org.example.domain.board.controller.response.ListBoardCategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListBoardService {

  private final ListBoardRepository listBoardRepository;

  /**
   * 게시글 카테고리 목록 조회
   */
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

  /**
   * 게시글 목록 조회
   */
  public ListBoardResponse getBoardList(SearchBoardRequest request) {
    Page<ListBoardDto> boardList = listBoardRepository.getBoardList(request);
    boardList.forEach(board -> board.updateCategory(board.getCategory()));

    return ListBoardResponse.builder()
      .boardList(boardList.getContent())
      .totalCount(boardList.getTotalElements())
      .saveCount(boardList.getTotalElements())
      .tempSaveCount(0)
      .build();
  }
        return ListBoardResponse.builder()
            .boardList(boardList.getContent())
            .totalCount(boardList.getTotalElements())
            .build();
    }

    /**
     * 임시저장 게시글 목록 조회
     */
    public ListBoardResponse getDraftBoardList() {
        List<ListBoardDto> boardList = listBoardRepository.getDraftBoardList();

        return ListBoardResponse.builder()
            .boardList(boardList)
            .totalCount(boardList.size())
            .build();
    }
}
