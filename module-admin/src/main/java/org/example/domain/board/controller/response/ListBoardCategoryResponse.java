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
@Schema(description = "카테고리 목록 조회 응답 객체")
public class ListBoardCategoryResponse {

  @Schema(description = "카테고리 목록")
  private List<ListBoardCategoryDto> categoryList;

}
