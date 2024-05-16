package org.example.domain.s3_file.repository;

import org.example.domain.s3_file.S3File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface S3FileRepository extends JpaRepository<S3File, Long> {

    void deleteByFileUrl(String fileUrl);
}
