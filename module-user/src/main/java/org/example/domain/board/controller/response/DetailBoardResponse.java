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
import lombok.Setter;
import org.example.util.CryptoUtils;
import org.example.util.DateUtils;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "게시판 목록 응답 DTO")
public class DetailBoardResponse {

  @Schema(description = "게시판 제목")
  private String title;

  @Schema(description = "게시판 내용")
  private String content;

  @Default
  @Setter
  @Schema(description = "게시판 파일 URL 목록")
  private List<String> fileUrlList = new ArrayList<>();

  @Schema(description = "작성자 이름")
  private String createdName;
  private LocalDateTime updatedTime;

  @Schema(description = "최종 수정일")
  public String getUpdatedTime() {
    return DateUtils.getUpdatedTime(updatedTime);
  }

  public void encryptCreatedName() {
    this.createdName = "익명" + CryptoUtils.encode(this.createdName).substring(0,4);
  }
}
