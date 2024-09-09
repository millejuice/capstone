package com.example.CAPSTONE1.faq.dto.response;

import com.example.CAPSTONE1.faq.entity.FAQ;
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

        public static ReadFAQResponse from(FAQ req){
            return new ReadFAQResponse(req.getTitle(), req.getContent(), req.getAnswerTitle(), req.getAnswerContent(), req.getAnswered());
        }
    }
}
