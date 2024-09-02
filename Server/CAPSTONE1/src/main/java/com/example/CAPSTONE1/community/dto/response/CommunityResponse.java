package com.example.CAPSTONE1.community.dto.response;

import com.example.CAPSTONE1.community.entity.Community;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommunityResponse {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReadPostResponse{
        private String nickname;
        private int viewCnt;
        private String title;
        private String content;

        public ReadPostResponse from(Community com){
            return ReadPostResponse.builder()
                    .nickname(com.getUser().getNickname())
                    .viewCnt(com.getViewCnt())
                    .title(com.getTitle())
                    .content(com.getContent())
                    .build();
        }
    }
}
