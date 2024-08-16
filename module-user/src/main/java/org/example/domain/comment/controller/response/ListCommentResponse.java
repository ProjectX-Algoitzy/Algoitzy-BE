package org.example.domain.comment.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "댓글 목록 응답 객체")
public class ListCommentResponse {

  @Default
  @Schema(description = "댓글 목록")
  private List<ListCommentDto> commentList = new ArrayList<>();

  @Schema(description = "총 댓글 수")
  private long totalCount;
}
