package com.example.CAPSTONE1.common.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommonException extends RuntimeException{
    private final ExceptionCode exceptionCode;

    public CommonException(ExceptionCode exceptionCode){
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}
