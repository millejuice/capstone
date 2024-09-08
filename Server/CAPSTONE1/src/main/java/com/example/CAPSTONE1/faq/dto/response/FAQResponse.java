package com.example.CAPSTONE1.faq.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class FAQResponse {
    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class ReadFAQResponse{
        private String qTitle;
        private String qContent;
        private String aTitle;
        private String aContent;
        private Boolean answered;
    }
}
