package com.botlabs.minio.controller;

import com.botlabs.minio.common.annotation.FileMaxSize;
import com.botlabs.minio.common.annotation.FileRequired;
import com.botlabs.minio.common.annotation.ValidFileType;
import com.botlabs.minio.controller.responses.BaseResponse;
import com.botlabs.minio.controller.responses.FileResponse;
import com.botlabs.minio.service.MinioFileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

/**
 * REST controller for managing File manager.
 */
@Slf4j
@RestController
@RequestMapping("/file")
@Validated
public class FileController {

    private final MinioFileStorageService minioFileStorageService;

    public FileController(MinioFileStorageService minioFileStorageService) {
        this.minioFileStorageService = minioFileStorageService;
    }


    @PostMapping("/upload")
    public ResponseEntity<BaseResponse> uploadFile(@RequestParam("file")
                                                   @FileRequired
                                                   @FileMaxSize
                                                   @ValidFileType MultipartFile file) {

        minioFileStorageService.uploadFile(file);
        return new ResponseEntity<>(new BaseResponse(new FileResponse(file.getOriginalFilename(), file.getContentType(), file.getSize()), "File uploaded with success!"), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse> deleteFile(@RequestParam("fileName") String fileName) {
        minioFileStorageService.deleteFile(fileName);

        return new ResponseEntity<>(new BaseResponse(null, "file [" + fileName + "] removing request submitted successfully."), HttpStatus.OK);
    }


    @PostMapping("/upload-files")
    public List<ResponseEntity<BaseResponse>> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.stream(files)
                .map(this::uploadFile)
                .toList();
    }

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadFile(@RequestParam("fileName") String fileName) {
        InputStreamResource resource = minioFileStorageService.downloadFile(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

}
