package com.botlabs.minio.service;

import com.botlabs.minio.configuration.MinioProperties;
import com.botlabs.minio.exception.FileStorageException;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@RequiredArgsConstructor
@Slf4j
@Service
public class MinioFileStorageServiceImpl implements MinioFileStorageService {

    private final MinioClient minioClient;

    private final MinioProperties minioProperties;

    /**
     * {@inheritDoc}
     */
    @Async
    @Override
    public void uploadFile(MultipartFile multipartFile) {
        try {
           var putObjectRequest = PutObjectArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .object(multipartFile.getOriginalFilename())
                    .stream(multipartFile.getInputStream(), multipartFile.getSize(), -1)
                    .contentType(multipartFile.getContentType())
                    .build();

            minioClient.putObject(putObjectRequest);

        } catch (Exception ex ) {
            log.error("error [{}] occurred while uploading file.", ex.getMessage());
            throw new FileStorageException("Could not upload file");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Async
    @Override
    public void deleteFile(String fileName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .object(fileName)
                    .build());
        } catch (Exception ex) {
            log.error("error [{}] occurred while removing [{}] ", ex.getMessage(), fileName);
            throw new FileStorageException("Error: Could not delete file");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputStreamResource downloadFile(String fileName)  {
        try {
           var inputStream = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .object(fileName)
                    .build());
            return new InputStreamResource(inputStream);
        } catch (Exception ex) {
            log.error("error [{}] occurred while download [{}] ", ex.getMessage(), fileName);
            throw new FileStorageException("Error: Could not download file");
        }

    }
}
