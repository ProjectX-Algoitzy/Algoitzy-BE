package org.example.domain.board.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.board.enums.BoardCategory;
import org.example.domain.board_file.controller.response.ListBoardFileDto;

@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "게시글 상세 조회 응답 객체")
public class DetailBoardResponse {

  @Schema(description = "카테고리 코드")
  private String categoryCode;

  @Schema(description = "카테고리명")
  private String category;

  @Schema(description = "게시글 제목")
  private String title;

  @Schema(description = "작성자 ID")
  private long createMemberId;

  @Schema(description = "작성자 프로필 이미지")
  private String profileUrl;

  @Schema(description = "작성자")
  private String createdName;

  @Schema(description = "작성일")
  private LocalDateTime createdTime;

  @Schema(description = "조회수")
  private int viewCount;

  @Schema(description = "게시글 ID")
  private long boardId;

  @Schema(description = "게시글 내용")
  private String content;

  @Default
  @Schema(description = "첨부파일 목록")
  private List<ListBoardFileDto> boardFileList = new ArrayList<>();

  @Schema(description = "좋아요 수")
  private int likeCount;

  @Schema(description = "내 좋아요 여부")
  private boolean myLikeYn;

  @Schema(description = "댓글 수")
  private int replyCount;

  @Schema(description = "최종 저장 여부")
  private boolean saveYn;

  @Schema(description = "고정 여부")
  private boolean fixYn;

  @Schema(description = "삭제 여부")
  private boolean deleteYn;

  public void updateCategory(String type) {
    this.category = BoardCategory.valueOf(type).getName();
  }
}
