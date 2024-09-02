package com.example.CAPSTONE1.comment.entity;

import com.example.CAPSTONE1.community.entity.Community;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10000)
    private String content;

    @ManyToOne(targetEntity = Community.class, fetch = FetchType.EAGER)
    private Community community;
}
