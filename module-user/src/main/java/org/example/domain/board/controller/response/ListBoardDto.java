package org.example.domain.board.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.util.DateUtils;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "게시판 목록 응답 DTO")
public class ListBoardDto {

  @Schema(description = "게시판 ID")
  private long boardId;

  @Schema(description = "게시판 제목")
  private String title;

  private LocalDateTime updatedTime;

  @Schema(description = "최종 수정일")
  public String getUpdatedTime() {
    return DateUtils.getUpdatedTime(updatedTime);
  }
}
