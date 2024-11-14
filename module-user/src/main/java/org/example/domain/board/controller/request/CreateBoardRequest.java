package org.example.domain.board.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.example.domain.board.enums.BoardCategory;

@Schema(description = "게시글 생성 요청 객체")
public record CreateBoardRequest(

    @Schema(description = "게시글 카테고리")
    BoardCategory category,

    @NotNull
    @Schema(description = "게시글 제목")
    String title,

    @NotNull
    @Schema(description = "게시글 내용")
    String content,

    @Schema(description = "첨부파일 URL 목록")
    List<String> fileUrlList,

    @NotNull
    @Schema(description = "게시글 임시저장 여부")
    Boolean saveYn,

    @NotNull
    @Schema(description = "게시글 작성자")
    Long memberId

) {

}
