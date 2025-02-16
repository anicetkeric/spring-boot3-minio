package com.botlabs.minio.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.botlabs.minio.common.annotation.FileRequired;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

public class FileRequiredValidator implements ConstraintValidator<FileRequired, MultipartFile> {
    @Override
    public void initialize(FileRequired constraint) {}

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
        return multipartFile != null
                && !Objects.requireNonNull(multipartFile.getOriginalFilename()).isEmpty();
    }
}