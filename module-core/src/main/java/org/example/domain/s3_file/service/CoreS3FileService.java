package org.example.domain.s3_file.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CoreS3FileService {

  private final CoreCreateS3FileService coreCreateS3FileService;

  /**
   * S3 파일 업로드
   */
  public List<String> uploadS3File(List<MultipartFile> multipartFileList) {
    return coreCreateS3FileService.uploadS3File(multipartFileList);
  }

  /**
   * S3 파일 삭제
   */
  public void deleteS3File(String fileName) {
    coreCreateS3FileService.deleteS3File(fileName);
  }

}
