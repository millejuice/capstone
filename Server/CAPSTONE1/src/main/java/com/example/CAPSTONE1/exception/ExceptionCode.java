package com.example.CAPSTONE1.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"No user information exists for that ID value."),
    CHATROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "No ChatRoom exists for that ID value"),
    INVALID_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "This Services need Login"),
    EMPTY_PAYLOAD_IN_TOKEN(HttpStatus.NOT_FOUND, "Token's payload is empty"),
    UNAUTHORIZED_MANAGER_TOKEN(HttpStatus.UNAUTHORIZED, "This User's Role is not MANAGER");

    private final HttpStatus httpStatus;
    private final String message;
}
