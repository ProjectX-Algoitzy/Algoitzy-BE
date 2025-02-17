package org.example.domain.board.repository;

import static org.example.domain.board.QBoard.board;
import static org.example.domain.board_like.QBoardLike.boardLike;
import static org.example.domain.member.QMember.member;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.example.domain.board.controller.response.ListBoardDto;
import org.example.domain.board.enums.BoardCategory;
import org.example.domain.board.enums.BoardSort;
import org.example.domain.board.controller.request.SearchBoardRequest;
import org.example.domain.member.Member;
import org.example.util.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class ListBoardRepository {

  private final JPAQueryFactory queryFactory;

  /**
   * 게시글 목록 조회
   */
  public Page<ListBoardDto> getBoardList(SearchBoardRequest request) {
    List<ListBoardDto> boardList =
      selectFields()
        .from(board)
        .leftJoin(boardLike).on(board.eq(boardLike.board))
        .where(
          searchCategory(request.category()),
          searchKeyword(request.searchKeyword()),
          board.saveYn.isTrue(),
          board.deleteYn.isFalse()
        )
        .groupBy(board)
        .offset(request.pageRequest().getOffset())
        .limit(request.pageRequest().getPageSize())
        .orderBy(
          new CaseBuilder()
            .when(board.fixYn.isTrue()).then(0)
            .otherwise(1).asc(),
          searchOrderBy(request.sort())
        )
        .fetch();

    JPAQuery<Long> countQuery = queryFactory
      .select(board.count())
      .from(board)
      .where(
        searchCategory(request.category()),
        searchKeyword(request.searchKeyword()),
        board.saveYn.isTrue(),
        board.deleteYn.isFalse()
      );

    return PageableExecutionUtils.getPage(boardList, request.pageRequest(), countQuery::fetchOne);

  }

  /**
   * 카테고리 검색
   */
  private static Predicate searchCategory(BoardCategory category) {
    if (category == null) {
      return null;
    }
    return board.category.eq(category);
  }

  /**
   * 키워드 검색(제목, 작성자)
   */
  private Predicate searchKeyword(String searchKeyword) {
    if (!StringUtils.hasText(searchKeyword)) {
      return null;
    }
    BooleanBuilder builder = new BooleanBuilder();
    builder.or(board.title.contains(searchKeyword));
    builder.or(board.member.name.contains(searchKeyword));
    return builder;
  }

  /**
   * 정렬 조건
   */
  private OrderSpecifier<?> searchOrderBy(BoardSort sort) {
    if (sort == null) {
      return board.createdTime.desc();
    }

    return switch (sort) {
      case LATEST -> board.createdTime.desc();
      case LIKE -> boardLike.count().desc();
      case VIEW_COUNT -> board.viewCount.desc();
    };
  }

  /**
   * 마이페이지 게시글 정보
   */
  public List<ListBoardDto> getMyPageBoard(String handle) {
    return selectFields()
      .from(board)
      .where(
        board.member.handle.eq(handle),
        board.category.ne(BoardCategory.NOTICE),
        filterMyBoard(handle)
      )
      .orderBy(board.createdTime.desc())
      .fetch();
  }

  /**
   * 나의 마이페이지가 아닌 경우, 관리자 삭제 글과 임시저장 글 미노출
   */
  private Predicate filterMyBoard(String handle) {
    Member currentMember = queryFactory
      .selectFrom(member)
      .where(member.email.eq(SecurityUtils.getCurrentMemberEmail()))
      .fetchOne();

    BooleanBuilder builder = new BooleanBuilder();

    // 나의 마이페이지 조회한 경우
    if (Objects.requireNonNull(currentMember).getHandle().equals(handle)) {
      return null;
    }

    // 타인의 마이페이지 조회한 경우
    return builder
      .and(board.deleteYn.isFalse())
      .and(board.saveYn.isTrue());
  }

  private JPAQuery<ListBoardDto> selectFields() {
    return queryFactory
      .select(
        Projections.fields(
          ListBoardDto.class,
          board.id.as("boardId"),
          board.category.stringValue().as("category"),
          board.title,
          board.member.name.as("createdName"),
          board.createdTime,
          Expressions.booleanTemplate(
            "case when {0} > {1} then true else false end",
            board.createdTime, LocalDateTime.now().minusDays(3)).as("newBoardYn"),
          board.viewCount,
          board.saveYn,
          board.fixYn,
          board.deleteYn
        )
      );
  }

  /**
   * 임시저장 게시글 목록 조회
   */
  public List<ListBoardDto> getDraftBoardList() {
    return queryFactory
        .select(Projections.fields(
            ListBoardDto.class,
            board.id.as("boardId"),
            board.category.stringValue().as("categoryCode"),
            board.category.stringValue().as("category"),
            board.title,
            board.member.name.as("createdName"),
            board.createdTime,
            Expressions.booleanTemplate(
                    "case when {0} > {1} then true else false end",
                    board.createdTime, LocalDateTime.now().minusDays(3))
                .as("newBoardYn"),
            board.viewCount,
            board.fixYn,
            board.deleteYn
            )
        )
        .from(board)
        .where(
            board.saveYn.isFalse(),
            board.category.ne(BoardCategory.NOTICE),
            board.member.email.eq(SecurityUtils.getCurrentMemberEmail())
        )
        .orderBy(board.updatedTime.desc())
        .fetch();
  }
}
