package com.example.CAPSTONE1.article.controller;

import com.example.CAPSTONE1.auth.login.NormalLogin;
import com.example.CAPSTONE1.auth.required.RequiredNormalLogin;
import com.example.CAPSTONE1.article.dto.request.ArticleRequest;
import com.example.CAPSTONE1.article.dto.response.ArticleResponse;
import com.example.CAPSTONE1.article.service.ArticleService;
import com.example.CAPSTONE1.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping("")
    @RequiredNormalLogin
    public void createPost(@NormalLogin User user, @RequestBody ArticleRequest.ArticleCreateRequest req){
        articleService.createArticle(user,req);
    }

    @PatchMapping("/{postId}")
    @RequiredNormalLogin
    public void updatePost(@NormalLogin User user, @RequestBody ArticleRequest.ArticleCreateRequest req, @PathVariable Long postId){
        articleService.updateArticle(user,req,postId);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ArticleResponse.ReadArticleWithComment> readPost(@PathVariable Long postId){
        return ResponseEntity.ok(articleService.readArticle(postId));
    }

    @GetMapping("")
    public ResponseEntity<Page<ArticleResponse.ArticleReadResponse>> findAllPost(@RequestParam int page){
        return ResponseEntity.ok(articleService.readAllArticle(page));
    }

    @DeleteMapping("/{postId}")
    @RequiredNormalLogin
    public void deletePost(@NormalLogin User user, @PathVariable Long postId){
        articleService.deleteArticle(user,postId);
    }
}
