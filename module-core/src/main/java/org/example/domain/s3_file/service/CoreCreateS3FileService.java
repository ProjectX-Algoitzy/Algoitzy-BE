package org.example.domain.s3_file.service;

import static com.amazonaws.services.s3.model.CannedAccessControlList.*;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.s3_file.S3File;
import org.example.domain.s3_file.repository.S3FileRepository;
import org.example.util.FileUtils;
import org.example.util.RandomUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class CoreCreateS3FileService {

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;
  private final AmazonS3 amazonS3;
  private final AmazonS3Client amazonS3Client;
  private final S3FileRepository s3FileRepository;


  /**
   * S3 파일 업로드
   */
  public List<String> uploadS3File(List<MultipartFile> multipartFileList) {
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
          .build());
      fileUrlList.add(fileUrl);
    }

    return fileUrlList;
  }

  /**
   * S3 파일 삭제
   */
  public void deleteS3File(String fileName) {
    amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
    s3FileRepository.deleteByFileUrl(fileName);
  }

  /**
   * 중복 방지를 위한 파일명 난수화
   */
  private String generateRandomFileName(String originalName) {
    String fileName;
    do {
      fileName = RandomUtils.getRandomString(16).concat(FileUtils.getFileExtension(originalName));
    } while (s3FileRepository.existsByFileName(fileName));

    return fileName;
  }

}
