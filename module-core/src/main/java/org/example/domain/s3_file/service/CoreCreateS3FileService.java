package org.example.domain.s3_file.service;

import static com.amazonaws.services.s3.model.CannedAccessControlList.*;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.s3_file.S3File;
import org.example.domain.s3_file.controller.response.UploadS3FileDto;
import org.example.domain.s3_file.controller.response.UploadS3FileResponse;
import org.example.domain.s3_file.enums.FileExtension;
import org.example.domain.s3_file.repository.S3FileRepository;
import org.example.util.FileUtils;
import org.example.util.RandomUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CoreCreateS3FileService {

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;
  @Value("${spring.profiles.name}")
  private String profile;
  private final AmazonS3 amazonS3;
  private final AmazonS3Client amazonS3Client;
  private final S3FileRepository s3FileRepository;

  private static final int _5MB = 5 * 1024 * 1024;

  /**
   * S3 파일 업로드
   */
  public List<String> uploadS3File(List<MultipartFile> multipartFileList) {
    validate(multipartFileList);

    List<String> fileUrlList = new ArrayList<>();
    for (MultipartFile multipartFile : multipartFileList) {
      String fileName = generateRandomFileName(multipartFile.getOriginalFilename());

      try (InputStream inputStream = multipartFile.getInputStream()) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());
        PutObjectRequest request = new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
          .withCannedAcl(PublicRead);
        amazonS3.putObject(request);
      } catch (IOException e) {
        throw new GeneralException(ErrorStatus.INTERNAL_ERROR, "파일 업로드에 실패했습니다.");
      }

      String fileUrl = amazonS3Client.getUrl(bucket, fileName).toString();

      s3FileRepository.save(
        S3File.builder()
          .originalName(multipartFile.getOriginalFilename())
          .fileName(fileName)
          .fileUrl(fileUrl)
          .fileSize(multipartFile.getSize())
          .build());
      fileUrlList.add(fileUrl);
    }

    return fileUrlList;
  }

  /**
   * S3 파일 업로드 v2
   */
  public UploadS3FileResponse uploadS3FileV2(List<MultipartFile> multipartFileList) {
    validate(multipartFileList);

    List<UploadS3FileDto> s3FileList = new ArrayList<>();
    for (MultipartFile multipartFile : multipartFileList) {
      String fileName = generateRandomFileName(multipartFile.getOriginalFilename());

      try (InputStream inputStream = multipartFile.getInputStream()) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());
        PutObjectRequest request = new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
          .withCannedAcl(PublicRead);
        amazonS3.putObject(request);
      } catch (IOException e) {
        throw new GeneralException(ErrorStatus.INTERNAL_ERROR, "파일 업로드에 실패했습니다.");
      }

      String fileUrl = amazonS3Client.getUrl(bucket, fileName).toString();

      s3FileRepository.save(
        S3File.builder()
          .originalName(multipartFile.getOriginalFilename())
          .fileName(fileName)
          .fileUrl(fileUrl)
          .fileSize(multipartFile.getSize())
          .build());
      s3FileList.add(
        UploadS3FileDto.builder()
          .originalName(multipartFile.getOriginalFilename())
          .fileUrl(fileUrl)
          .fileSize(multipartFile.getSize())
          .build()
      );
    }

    return UploadS3FileResponse.builder()
      .s3FileList(s3FileList)
      .build();
  }

  private void validate(List<MultipartFile> multipartFileList) {
    for (MultipartFile multipartFile : multipartFileList) {
      if (multipartFile.getSize() >= _5MB)
        throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "파일 용량은 5MB를 초과할 수 없습니다.");

      String fileExtension = FileUtils.getFileExtension(multipartFile.getOriginalFilename());
      if (!FileExtension.exist(fileExtension))
        throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, fileExtension + " 확장자는 허용되지 않습니다.");
    }
  }

  /**
   * 중복 방지를 위한 파일명 난수화
   */
  private String generateRandomFileName(String originalName) {
    String fileName;
    do {
      fileName = RandomUtils.getRandomString(16).concat(".").concat(FileUtils.getFileExtension(originalName));
    } while (s3FileRepository.existsByFileName(fileName));

    return profile + File.separator + fileName;
  }

  /**
   * S3 파일 삭제
   */
  public void deleteS3File(String fileUrl) {
    // 기본 이미지 삭제 안 함
    if (FileUtils.isBasicImage(fileUrl)) return;

    String fileName = extractFileNameFromUrl(fileUrl);
    fileName = profile + File.separator + fileName;

    // S3에서 파일 삭제
    amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));

    // 레포지토리에서 파일 삭제
    s3FileRepository.deleteByFileName(fileName);
  }

  /**
   * 파일 URL에서 파일 이름을 추출
   */
  private String extractFileNameFromUrl(String fileUrl) {
    int fileIndex = fileUrl.lastIndexOf('/');
    return fileUrl.substring(fileIndex + 1);
  }
}
