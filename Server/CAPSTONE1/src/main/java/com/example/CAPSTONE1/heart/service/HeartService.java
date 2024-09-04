package com.example.CAPSTONE1.heart.service;

import com.example.CAPSTONE1.article.entity.Article;
import com.example.CAPSTONE1.article.repo.ArticleRepo;
import com.example.CAPSTONE1.common.exception.CommonException;
import com.example.CAPSTONE1.common.exception.ExceptionCode;
import com.example.CAPSTONE1.heart.dto.request.HeartRequest;
import com.example.CAPSTONE1.heart.repo.HeartRepo;
import com.example.CAPSTONE1.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HeartService {
    private final HeartRepo heartRepo;
    private final ArticleRepo articleRepo;

    public boolean alreadyLiked(Article article,User user){
        return false;
    }

    public void updateLike(User user, Long articleId){
        Optional<Article> opArc = articleRepo.findById(articleId);
        if(opArc.isEmpty()){throw new CommonException(ExceptionCode.POST_NOT_EXIST_IN_COMMUNITY);}
        Article article = opArc.get();
        boolean liked = alreadyLiked(article, user);
        if(liked){
            article.setLikeCnt(article.getLikeCnt()+1);
        } else {
            article.setLikeCnt(article.getLikeCnt()-1);
        }
    }
}
