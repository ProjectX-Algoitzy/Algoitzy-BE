package org.example.domain.s3_file.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.example.domain.s3_file.S3File;
import org.example.domain.s3_file.repository.S3FileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional
public class CoreCreateS3FileService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;
    private final S3FileRepository s3FileRepository;


    public List<String> uploadFile(List<MultipartFile> multipartFileList) {

        List<String> fileList = new ArrayList<>();

        multipartFileList.forEach(multipartFile -> {
            String fileUrl = generateRandomFileName(multipartFile.getOriginalFilename());

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(multipartFile.getSize());
            objectMetadata.setContentType(multipartFile.getContentType());

            s3FileRepository.save(
                S3File.builder()
                    .originalName(multipartFile.getOriginalFilename())
                    .fileUrl(fileUrl)
                    .build());

            try (InputStream inputStream = multipartFile.getInputStream()) {
                amazonS3.putObject(
                    new PutObjectRequest(bucket, fileUrl, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "파일 업로드에 실패했습니다.");

            }
            fileList.add(fileUrl);
        });

        return fileList;
    }

    public void deleteFileFromS3(String fileUrl) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileUrl));
        s3FileRepository.deleteByFileUrl(fileUrl);
    }

    /*
    * 중복 방지를 위한 파일명 난수화
    */
    private String generateRandomFileName(String fileUrl) {
        return UUID.randomUUID().toString().concat(extractFileExtension(fileUrl));
    }

    /*
    * 파일이 유효한지 검사
    * 모든 파일을 구분없이 받기 위해 .확장자 검사
    * */
    private String extractFileExtension(String fileUrl) {
        if (fileUrl == null || !fileUrl.contains(".")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileUrl + ") 입니다.");
        }
        return fileUrl.substring(fileUrl
            .lastIndexOf("."));
    }
}
