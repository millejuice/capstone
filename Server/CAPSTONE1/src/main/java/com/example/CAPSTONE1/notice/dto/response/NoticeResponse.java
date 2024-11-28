package com.example.CAPSTONE1.notice.dto.response;

import com.example.CAPSTONE1.notice.entity.Notice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class NoticeResponse {
    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class NoticeReadResponse{
        private Long id;
        private String title;
        private String content;
        private boolean pin;
        private int viewCnt;
        public static NoticeReadResponse of(Notice notice){
            return NoticeReadResponse.builder()
                    .id(notice.getId())
                    .title(notice.getTitle())
                    .content(notice.getContent())
                    .pin(notice.isPin())
                    .viewCnt(notice.getViewCnt())
                    .build();
        }
    }
}
