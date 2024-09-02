package com.example.CAPSTONE1.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CapstoneExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ExceptionResponse> handleException(CommonException e){
        return new ResponseEntity<>(ExceptionResponse.builder()
                .httpStatus(e.getExceptionCode().getHttpStatus())
                .message(e.getExceptionCode().getMessage())
                .build(), e.getExceptionCode().getHttpStatus());
    }
}
