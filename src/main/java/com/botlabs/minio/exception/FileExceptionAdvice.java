package com.botlabs.minio.exception;

import com.botlabs.minio.controller.responses.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import jakarta.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class FileExceptionAdvice {

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<BaseResponse> handleFileNotFoundException(FileNotFoundException exc) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse(null, exc.getMessage()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public BaseResponse notSupportedException(HttpRequestMethodNotSupportedException ex) {
        log.error(ex.getMessage(), ex.getCause());
        return new BaseResponse(null, HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse handleAllExceptions(Exception ex) {
        log.error(ex.getMessage(), ex.getLocalizedMessage());
        return new BaseResponse(null, (ex.getMessage() != null) ? ex.getMessage() : HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<BaseResponse> noHandlerFoundException(NoHandlerFoundException ex) {
        log.error(ex.getMessage(), ex.getCause());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse(null, "Resource not found"));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new BaseResponse(null, exc.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ConstraintViolationException.class})
    public BaseResponse handleValidationError(ConstraintViolationException ex) {
        return new BaseResponse(null, ex.getMessage());
    }

}
