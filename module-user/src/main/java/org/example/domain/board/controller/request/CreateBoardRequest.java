package org.example.domain.board.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "게시판 생성 요청 객체")
public record CreateBoardRequest(

  @NotBlank
  @Schema(description = "게시판 제목")
  String title,

  @NotBlank
  @Schema(description = "게시판 내용")
  String content
) {

}
