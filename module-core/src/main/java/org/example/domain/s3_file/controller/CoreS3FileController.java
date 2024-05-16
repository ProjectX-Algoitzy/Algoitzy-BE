package org.example.domain.s3_file.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api_response.ApiResponse;
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
@Slf4j
@Tag(name = "CoreS3FileController", description = "S3 파일 업로드 관련 API")
public class CoreS3FileController {

    private final CoreS3FileService coreS3FileService;

    /*
     * Amazon S3에 파일 업로드
     * */
    @PostMapping(value ="/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "S3에 파일 업로드")
    public ApiResponse<List<String>> uploadFile(@RequestPart List<MultipartFile> multipartFileList) {
        return ApiResponse.onCreate(coreS3FileService.uploadS3File(multipartFileList));
    }

    /*
    *  S3에서 파일 삭제
    * */
    @DeleteMapping("/delete")
    @Operation(summary = "S3에서 파일 삭제")
    public ApiResponse<Void> deleteFile(@RequestParam String fileName) {
        coreS3FileService.deleteFileFromS3(fileName);
        return ApiResponse.onSuccess();
    }
}
