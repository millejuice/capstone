package com.example.CAPSTONE1.heart.repo;

import com.example.CAPSTONE1.article.entity.Article;
import com.example.CAPSTONE1.heart.entity.Heart;
import com.example.CAPSTONE1.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeartRepo extends JpaRepository<Heart,Long> {

    Optional<Heart> findHeartByArticleAndUser(Article article, User user);
}
