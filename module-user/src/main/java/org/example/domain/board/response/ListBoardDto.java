package org.example.domain.board.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "게시판 목록 응답 객체")
public class ListBoardDto {

    @Schema(description = "게시판 ID")
    private long boardId;

}
