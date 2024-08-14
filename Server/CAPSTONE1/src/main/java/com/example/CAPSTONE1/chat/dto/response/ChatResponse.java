package com.example.CAPSTONE1.chat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ChatResponse {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChatMessageResponse{
        private Long id;
        private String sender;
        private String message;
    }
}
