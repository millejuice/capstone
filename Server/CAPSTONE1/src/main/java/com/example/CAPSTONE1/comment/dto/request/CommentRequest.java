package com.example.CAPSTONE1.comment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommentRequest {
    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class WriteComment{
        private String content;
    }
}
