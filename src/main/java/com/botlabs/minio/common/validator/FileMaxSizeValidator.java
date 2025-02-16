package com.botlabs.minio.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.botlabs.minio.common.annotation.FileMaxSize;
import org.springframework.web.multipart.MultipartFile;

public class FileMaxSizeValidator implements
        ConstraintValidator<FileMaxSize, MultipartFile> {

    private FileMaxSize constraint;

    @Override
    public void initialize(FileMaxSize constraint) {
        this.constraint = constraint;
    }

    @Override
    public boolean isValid(MultipartFile multipartFile,
                           ConstraintValidatorContext context) {
        if (constraint.value() < 0 || multipartFile == null) {
            return true;
        }
        return multipartFile.getSize() <= constraint.value();
    }

}
