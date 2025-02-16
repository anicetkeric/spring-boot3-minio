package com.botlabs.minio.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

public interface MinioFileStorageService {

    /**
     * @param file MultipartFile file
     */
    void uploadFile(MultipartFile file);


    /**
     * Delete file from S3Bucket
     * @param fileName file name
     */
    void deleteFile(String fileName);


    /**
     * @param fileName file name
     * @return InputStreamResource
     */
    InputStreamResource downloadFile(String fileName);
}
