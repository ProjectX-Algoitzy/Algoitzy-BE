package org.example.domain.board.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "게시글 생성 요청 객체")
public record CreateBoardRequest(

  @Schema(description = "게시글 제목")
  String title,

  @NotBlank
  @Schema(description = "내용(에디터)")
  String content,

  @Schema(description = "첨부파일 URL 목록")
  List<String> fileUrlList,

  @NotNull
  @Schema(description = "공지사항 최종 저장 여부")
  Boolean saveYn

) {

}
