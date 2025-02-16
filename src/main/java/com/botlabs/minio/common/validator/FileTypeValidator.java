package com.botlabs.minio.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.botlabs.minio.common.FileUtils;
import com.botlabs.minio.common.annotation.ValidFileType;
import org.springframework.web.multipart.MultipartFile;

public class FileTypeValidator implements ConstraintValidator<ValidFileType, MultipartFile> {

    private String message;

    @Override
    public void initialize(ValidFileType constraint) {
        this.message = constraint.message();

    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {

        if (!FileUtils.isValid(multipartFile)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    message)
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

}