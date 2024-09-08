package com.example.CAPSTONE1.common.exception;

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
    UNAUTHORIZED_MANAGER_TOKEN(HttpStatus.UNAUTHORIZED, "This User's Role is not MANAGER"),
    USER_ID_NOT_EQUAL(HttpStatus.UNAUTHORIZED, "작성한 유저가 아닙니다"),
    NO_AUTHORIZATION_OF_DELETING_POST(HttpStatus.UNAUTHORIZED, "해당 게시물을 삭제할 권한이 없습니다"),
    COMMENT_NOT_EXIST(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다"),
    NO_AUTHORIZATION_OF_DELETING_COMMENT(HttpStatus.UNAUTHORIZED, "해당 댓글을 삭제할 권한이 없습니다"),
    FAQ_NOT_EXIST(HttpStatus.NOT_FOUND, "FAQ를 찾을수 없습니다"),
    POST_NOT_EXIST_IN_COMMUNITY(HttpStatus.NOT_FOUND, "해당 게시물이 존재하지 않습니다");


    private final HttpStatus httpStatus;
    private final String message;
}
