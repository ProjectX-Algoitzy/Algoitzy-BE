package org.example.domain.s3_file.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.s3_file.controller.response.UploadS3FileResponse;
import org.example.domain.s3_file.service.CoreS3FileService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/s3")
@RequiredArgsConstructor
@Tag(name = "CoreS3FileController", description = "S3 파일 관련 API")
public class CoreS3FileController {

  private final CoreS3FileService coreS3FileService;

  @Deprecated
  @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  @Operation(summary = "S3 파일 업로드")
  public ApiResponse<List<String>> uploadS3File(@RequestPart List<MultipartFile> multipartFileList) {
    return ApiResponse.onCreate(coreS3FileService.uploadS3File(multipartFileList));
  }

  @PostMapping(value = "/v2", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  @Operation(summary = "S3 파일 업로드 v2")
  public ApiResponse<UploadS3FileResponse> uploadS3FileV2(@RequestPart List<MultipartFile> multipartFileList) {
    return ApiResponse.onCreate(coreS3FileService.uploadS3FileV2(multipartFileList));
  }

  @DeleteMapping("")
  @Operation(summary = "S3 파일 삭제")
  public ApiResponse<Void> deleteS3File(@RequestParam String fileUrl) {
    coreS3FileService.deleteS3File(fileUrl);
    return ApiResponse.onSuccess();
  }
}
