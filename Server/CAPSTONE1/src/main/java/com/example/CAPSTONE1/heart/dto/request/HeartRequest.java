package com.example.CAPSTONE1.heart.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class HeartRequest {
    private Long userId;
    private Long articleId;
}
