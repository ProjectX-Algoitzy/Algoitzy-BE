package org.example.domain.board.request;


import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Min;
import org.example.domain.board.enums.BoardCategory;
import org.example.domain.board.enums.BoardSort;
import org.springframework.data.domain.PageRequest;

@Schema(description = "게시글 목록 검색 요청 객체")
public record SearchBoardRequest(

    @Schema(description = "검색 키워드(게시글 제목, 게시글 내용, 작성자)")
    String searchKeyword,

    @Schema(description = "게시글 카테고리")
    BoardCategory category,

    @Schema(description = "게시글 정렬 조건", allowableValues = {"LATEST", "LIKE", "VIEW_COUNT"})
    BoardSort sort,

    @Min(1)
    @Schema(description = "페이지 번호", type = "integer", requiredMode = RequiredMode.REQUIRED)
    int page,

    @Min(10)
    @Schema(description = "페이지별 개수", type = "integer", requiredMode = RequiredMode.REQUIRED)
    int size
) {

    public PageRequest pageRequest() {
        return PageRequest.of(page - 1, size);
    }

}