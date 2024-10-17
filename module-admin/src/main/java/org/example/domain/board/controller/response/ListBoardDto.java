package org.example.domain.board.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.board.enums.BoardCategory;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "게시글 목록 조회 응답 DTO")
public class ListBoardDto {

  @Schema(description = "게시글 ID")
  private long boardId;

  @Schema(description = "카테고리")
  private String category;

  @Schema(description = "제목")
  private String title;

  @Schema(description = "작성자")
  private String createdName;

  @Schema(description = "작성일")
  private LocalDateTime createdTime;

  @Schema(description = "새 게시물 여부")
  private boolean newBoardYn;

  @Schema(description = "조회수")
  private int viewCount;

  @Schema(description = "고정 여부")
  private boolean fixYn;

  @Schema(description = "삭제 여부")
  private boolean deleteYn;

  public void updateCategory(String type) {
    this.category = BoardCategory.valueOf(type).getName();
  }
}
