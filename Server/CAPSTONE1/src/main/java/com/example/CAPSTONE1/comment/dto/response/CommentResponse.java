package com.example.CAPSTONE1.comment.dto.response;

import com.example.CAPSTONE1.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommentResponse {
    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class CommentReadResponse{
        private Long commentId;
        private String nickname;
        private String content;

        public static CommentReadResponse from(Comment comment){
            return CommentReadResponse.builder()
                    .commentId(comment.getId())
                    .nickname(comment.getUser().getNickname())
                    .content(comment.getContent())
                    .build();
        }
    }
}
