package org.example.domain.board_file.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.util.FileUtils;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "게시글 상세 첨부파일 목록 객체")
public class ListBoardFileDto {

  @Schema(description = "첨부파일명")
  private String originalName;

  @Schema(description = "S3 파일 경로")
  private String fileUrl;

  private double fileSize;

  @Schema(description = "S3 파일 용량")
  public String getFileSize() {
    return FileUtils.getFileSize(fileSize);
  }
}
