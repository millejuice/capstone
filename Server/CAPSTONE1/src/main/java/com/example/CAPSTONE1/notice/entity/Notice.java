package com.example.CAPSTONE1.notice.entity;

import com.example.CAPSTONE1.common.BaseTimeEntity;
import com.example.CAPSTONE1.notice.dto.request.NoticeRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Notice extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 10000)
    private String content;

    private boolean pin;

    private int viewCnt;
    public void increaseViewCnt(){this.viewCnt++;}

    public static Notice from(NoticeRequest.NoticeCreateRequest req){
        return Notice.builder()
                .id(null)
                .title(req.getTitle())
                .content(req.getContent())
                .pin(req.isPinned())
                .build();
    }

    public static Notice updatePinned(Long noticeId,NoticeRequest.NoticeCreateRequest notice, boolean pinned){
        return Notice.builder()
                .id(noticeId)
                .title(notice.getTitle())
                .content(notice.getContent())
                .pin(pinned)
                .build();
    }
}
