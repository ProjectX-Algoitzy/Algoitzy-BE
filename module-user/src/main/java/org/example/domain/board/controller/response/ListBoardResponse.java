package org.example.domain.board.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "게시판 목록 응답 객체")
public class ListBoardResponse {

  @Schema(description = "게시판 목록 객체 리스트")
  List<ListBoardDto> boardList;

  @Schema(description = "게시판 총 개수")
  long totalCount;
}
