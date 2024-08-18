package com.example.CAPSTONE1.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"No user information exists for that ID value."),
    CHATROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "No ChatRoom exists for that ID value");

    private final HttpStatus httpStatus;
    private final String message;
}
