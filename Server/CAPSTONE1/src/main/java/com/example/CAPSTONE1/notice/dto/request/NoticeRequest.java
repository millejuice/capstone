package com.example.CAPSTONE1.notice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class NoticeRequest {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NoticeCreateRequest{
        private String title;
        private String content;
        private boolean pinned;
    }
}
