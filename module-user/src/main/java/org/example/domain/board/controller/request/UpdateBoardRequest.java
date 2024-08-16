package org.example.domain.board.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;

@Schema(description = "게시판 생성 요청 객체")
public record UpdateBoardRequest(

  @Schema(description = "게시판 제목")
  String title,

  @Schema(description = "게시판 내용")
  String content,

  List<String> fileUrlList
) {

  @Builder
  public UpdateBoardRequest {
    if (fileUrlList == null) {
      fileUrlList = new ArrayList<>();
    }
  }
}
