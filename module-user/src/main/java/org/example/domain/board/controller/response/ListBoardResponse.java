package org.example.domain.board.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "게시글 목록 조회 응답 객체")
public class ListBoardResponse {

    @Default
    @Schema(description = "게시글 목록")
    private List<ListBoardDto> boardList = new ArrayList<>();

    @Schema(description = "총 게시글 수")
    private long totalCount;

    @Schema(description = "최종 저장 게시글 수")
    private long saveCount;

    @Schema(description = "임시 저장 게시글 수")
    private long tempSaveCount;

}
