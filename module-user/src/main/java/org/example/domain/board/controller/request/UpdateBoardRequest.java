package org.example.domain.board.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "게시글 수정 요청 객체")
public record UpdateBoardRequest(
    @Schema(description = "게시글 제목")
    String title,

    @Schema(description = "내용(에디터)")
    String content,

    @Schema(description = "첨부파일 URL 목록")
    List<String> fileUrlList
    ) {
}
