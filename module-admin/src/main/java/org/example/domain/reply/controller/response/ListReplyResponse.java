package org.example.domain.reply.controller.response;

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
@Schema(description = "게시글 댓글 목록 조회 응답 객체")
public class ListReplyResponse {

  @Schema(description = "댓글 목록")
  private List<ListReplyDto> replyList;

  @Schema(description = "최상위 댓글 수")
  private long parentReplyCount;
}
