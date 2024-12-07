package org.example.domain.s3_file.controller.response;

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
@Schema(description = "S3 파일 업로드 응답 객체")
public class UploadS3FileResponse {

  @Default
  @Schema(description = "S3 파일 목록")
  List<UploadS3FileDto> s3FileList = new ArrayList<>();
}
