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
import org.example.domain.member.enums.Role;
import org.example.domain.member.service.CoreMemberService;
import org.example.domain.study_member.repository.DetailStudyMemberRepository;
import org.example.util.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListBoardService {

  private final CoreMemberService coreMemberService;
  private final ListBoardRepository listBoardRepository;
  private final DetailStudyMemberRepository detailStudyMemberRepository;

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
    for (ListBoardDto board : boardList) {
      board.updateCategory(board.getCategory());

      // 정규 스터디 참여 이력 없으면 작성자 이름 미노출
      if (!detailStudyMemberRepository.isRegularStudyMember()
        && coreMemberService.findByEmail(SecurityUtils.getCurrentMemberEmail()).getRole().equals(Role.ROLE_USER)) {
        board.blindCreatedName();
      }
    }

    return ListBoardResponse.builder()
      .boardList(boardList.getContent())
      .totalCount(boardList.getTotalElements())
      .saveCount(boardList.getTotalElements())
      .tempSaveCount(0)
      .build();
  }

  /**
   * 임시저장 게시글 목록 조회
   */
  public ListBoardResponse getDraftBoardList() {
    List<ListBoardDto> boardList = listBoardRepository.getDraftBoardList();
    boardList.forEach(board -> board.updateCategory(board.getCategory()));

    return ListBoardResponse.builder()
      .boardList(boardList)
      .totalCount(boardList.size())
      .saveCount(0)
      .tempSaveCount(boardList.size())
      .build();
  }
}
