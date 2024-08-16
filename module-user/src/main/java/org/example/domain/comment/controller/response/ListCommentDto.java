package org.example.domain.comment.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.util.CryptoUtils;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "댓글 목록 응답 DTO")
public class ListCommentDto {

  @Schema(description = "댓글 ID")
  private long commentId;

  @Schema(description = "댓글 작성자")
  private String createdName;

  @Schema(description = "내용")
  private String content;

  public void encryptCreatedName() {
    this.createdName = "익명" + CryptoUtils.encode(this.createdName).substring(0,4);
  }
}
