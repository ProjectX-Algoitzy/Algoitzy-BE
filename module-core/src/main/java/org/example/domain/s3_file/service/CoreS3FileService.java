package org.example.domain.s3_file.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CoreS3FileService {

    private final CoreCreateS3FileService coreCreateS3FileService;

    /*
     *  이미지 업로드 요청
     * */
    public List<String> uploadS3File(List<MultipartFile> multipartFileList) {
        return coreCreateS3FileService.uploadFile(multipartFileList);
    }

    /*
     *  이미지 삭제 요청
     * */
    public void deleteFileFromS3(String fileName) {
        coreCreateS3FileService.deleteFileFromS3(fileName);
    }

}
