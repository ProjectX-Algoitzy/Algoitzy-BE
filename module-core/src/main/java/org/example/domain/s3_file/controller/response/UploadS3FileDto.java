package org.example.domain.s3_file.controller.response;

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
@Schema(description = "S3 파일 업로드 응답 DTO")
public class UploadS3FileDto {

  @Schema(description = "S3 파일 원본명")
  private String originalName;

  @Schema(description = "S3 파일 경로")
  private String fileUrl;

  private double fileSize;

  @Schema(description = "S3 파일 용량")
  public String getFileSize() {
    return FileUtils.getFileSize(fileSize);
  }
}
