package com.example.CAPSTONE1.article.repo;

import com.example.CAPSTONE1.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepo extends JpaRepository<Article,Long> {
}
