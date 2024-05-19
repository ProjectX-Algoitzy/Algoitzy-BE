package org.example.domain.s3_file.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.s3_file.S3File;
import org.example.domain.s3_file.repository.S3FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CoreS3FileService {

  private final CoreCreateS3FileService coreCreateS3FileService;
  private final S3FileRepository s3FileRepository;

  public S3File findByFileUrl(String fileUrl) {
    return s3FileRepository.findByFileUrl(fileUrl)
      .orElseThrow(() -> new GeneralException(ErrorStatus.BAD_REQUEST, "존재하지 않는 File Url입니다."));
  }

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
