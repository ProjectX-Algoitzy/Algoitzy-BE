package org.example.domain.s3_file.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CoreCreateS3FileService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    public List<String> uploadFile(List<MultipartFile> multipartFileList) {

        List<String> fileList = new ArrayList<>();

        System.out.println("fileList = " + fileList);

        multipartFileList.forEach( file -> {
            String fileName = generateRandomFileName(file.getOriginalFilename());

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try (InputStream inputStream = file.getInputStream()) {
                amazonS3.putObject(
                    new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "파일 업로드에 실패했습니다.");
            }
            fileList.add(fileName);

            // todo s3File entity 저장
        });

        return fileList;
    }

    public void deleteFileFromS3(String fileName) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
        // todo s3File entity 삭제
    }

    /*
    * 중복 방지를 위한 파일명 난수화
    */
    private String generateRandomFileName(String fileName) {
        return UUID.randomUUID().toString().concat(extractFileExtension(fileName));
    }

    /*
    * 파일이 유효한지 검사
    * 모든 파일을 구분없이 받기 위해 .확장자 검사
    * */
    private String extractFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
