package com.example.CAPSTONE1.article.entity;

import com.example.CAPSTONE1.comment.entity.Comment;
import com.example.CAPSTONE1.common.BaseTimeEntity;
import com.example.CAPSTONE1.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 10000)
    private String content;

    private int viewCnt;

    private int likeCnt;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(targetEntity = Comment.class, fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "ARTICLE_ID")
    private List<Comment> comments;

    public void increaseViewCnt(){this.viewCnt++;}

    public static Article from(User user, String title, String content){
        return Article.builder()
                .id(null)
                .title(title)
                .content(content)
                .user(user)
                .build();
    }

}
