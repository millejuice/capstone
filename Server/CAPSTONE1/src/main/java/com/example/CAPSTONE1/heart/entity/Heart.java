package com.example.CAPSTONE1.heart.entity;

import com.example.CAPSTONE1.article.entity.Article;
import com.example.CAPSTONE1.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@AllArgsConstructor
public class Heart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = User.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(targetEntity = Article.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "ARTICLE_ID")
    private Article article;

    public static Heart from(User user, Article article){
        return new Heart(null, user, article);
    }
}
