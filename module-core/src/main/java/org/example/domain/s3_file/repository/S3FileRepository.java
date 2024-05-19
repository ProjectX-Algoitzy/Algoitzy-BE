package org.example.domain.s3_file.repository;

import java.util.Optional;
import org.example.domain.s3_file.S3File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface S3FileRepository extends JpaRepository<S3File, Long> {

    void deleteByFileUrl(String fileUrl);

    boolean existsByFileName(String fileName);

    Optional<S3File> findByFileUrl(String fileUrl);
}
