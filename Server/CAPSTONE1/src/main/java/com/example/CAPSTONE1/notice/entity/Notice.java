package com.example.CAPSTONE1.notice.entity;

import com.example.CAPSTONE1.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
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
}
