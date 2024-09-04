package com.example.CAPSTONE1.comment.entity;

import com.example.CAPSTONE1.comment.dto.request.CommentRequest;
import com.example.CAPSTONE1.common.BaseTimeEntity;
import com.example.CAPSTONE1.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10000)
    private String content;

    @ManyToOne(targetEntity = User.class,fetch = FetchType.EAGER)
    private User user;

    public static Comment from(User user,CommentRequest.WriteComment req){
        return Comment.builder()
                .id(null)
                .content(req.getContent())
                .user(user)
                .build();
    }
}
