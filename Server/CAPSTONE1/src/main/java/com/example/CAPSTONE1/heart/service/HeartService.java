package com.example.CAPSTONE1.heart.service;

import com.example.CAPSTONE1.article.entity.Article;
import com.example.CAPSTONE1.article.repo.ArticleRepo;
import com.example.CAPSTONE1.common.exception.CommonException;
import com.example.CAPSTONE1.common.exception.ExceptionCode;
import com.example.CAPSTONE1.heart.dto.request.HeartRequest;
import com.example.CAPSTONE1.heart.entity.Heart;
import com.example.CAPSTONE1.heart.repo.HeartRepo;
import com.example.CAPSTONE1.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HeartService {
    private final HeartRepo heartRepo;
    private final ArticleRepo articleRepo;

    public void updateLike(User user, Long articleId){
        Optional<Article> opArc = articleRepo.findById(articleId);
        if(opArc.isEmpty()){throw new CommonException(ExceptionCode.POST_NOT_EXIST_IN_COMMUNITY);}
        Article article = opArc.get();
        Optional<Heart> h = heartRepo.findHeartByArticleAndUser(article, user); //이미 좋아요 누른 기록이 존재하냐?
        if(h.isPresent()){
            article.setLikeCnt(article.getLikeCnt()-1);
            Heart heart = h.get();
            heartRepo.delete(heart);
        } else {
            article.setLikeCnt(article.getLikeCnt()+1);
            heartRepo.save(Heart.from(user, article));
        }
    }
}
