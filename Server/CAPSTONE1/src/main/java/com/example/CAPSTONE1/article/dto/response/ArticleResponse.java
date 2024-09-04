package com.example.CAPSTONE1.article.dto.response;

import com.example.CAPSTONE1.article.entity.Article;
import com.example.CAPSTONE1.comment.dto.response.CommentResponse;
import com.example.CAPSTONE1.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class ArticleResponse {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ArticleReadResponse{
        private Long id;
        private String nickname;
        private int viewCnt;
        private String title;
        private String content;

        public ArticleReadResponse from(Article com){
            return ArticleReadResponse.builder()
                    .id(com.getId())
                    .nickname(com.getUser().getNickname())
                    .viewCnt(com.getViewCnt())
                    .title(com.getTitle())
                    .content(com.getContent())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class ReadArticleWithComment{
        private Long id;
        private String nickname;
        private int viewCnt;
        private String title;
        private String content;
        private List<CommentResponse.CommentReadResponse> comments;

        public ReadArticleWithComment from(Article article, List<CommentResponse.CommentReadResponse> req){
            return ReadArticleWithComment.builder()
                    .id(article.getId())
                    .nickname(article.getUser().getNickname())
                    .viewCnt(article.getViewCnt())
                    .title(article.getTitle())
                    .content(article.getContent())
                    .comments(req)
                    .build();
        }
    }
}
