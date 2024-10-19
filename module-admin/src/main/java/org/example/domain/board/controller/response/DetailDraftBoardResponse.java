package org.example.domain.board.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.board_file.controller.response.ListBoardFileDto;

@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "임시저장 게시글 상세 조회 응답 객체")
public class DetailDraftBoardResponse {

  @Schema(description = "게시글 제목")
  private String title;

  @Schema(description = "게시글 ID")
  private long boardId;

  @Schema(description = "게시글 내용")
  private String content;

  @Default
  @Schema(description = "첨부파일 목록")
  private List<ListBoardFileDto> boardFileList = new ArrayList<>();

  @Schema(description = "임시저장 여부")
  private boolean saveYn;
}
