package com.example.CAPSTONE1.article.service;

import com.example.CAPSTONE1.comment.dto.response.CommentResponse;
import com.example.CAPSTONE1.comment.entity.Comment;
import com.example.CAPSTONE1.common.exception.CommonException;
import com.example.CAPSTONE1.common.exception.ExceptionCode;
import com.example.CAPSTONE1.article.dto.request.ArticleRequest;
import com.example.CAPSTONE1.article.dto.response.ArticleResponse;
import com.example.CAPSTONE1.article.entity.Article;
import com.example.CAPSTONE1.article.repo.ArticleRepo;
import com.example.CAPSTONE1.user.entity.ROLE;
import com.example.CAPSTONE1.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepo articleRepo;

    @Transactional
    public void createArticle(User user, ArticleRequest.ArticleCreateRequest req){
        articleRepo.save(Article.from(user, req.getTitle(), req.getContent()));
    }

    @Transactional
    public void updateArticle(User user, ArticleRequest.ArticleCreateRequest req, Long postId){
        Optional<Article> community = articleRepo.findById(postId);
        if(community.isEmpty()){throw new CommonException(ExceptionCode.POST_NOT_EXIST_IN_COMMUNITY);}
        if(!Objects.equals(community.get().getUser().getId(), user.getId())){throw new CommonException(ExceptionCode.USER_ID_NOT_EQUAL);}
        Article entity = community.get();
        entity.setTitle(req.getTitle());
        entity.setContent(req.getContent());
        articleRepo.save(entity);
    }

    @Transactional
    public ArticleResponse.ReadArticleWithComment readArticle(Long postId){
        Optional<Article> community = articleRepo.findById(postId);
        if(community.isEmpty()){throw new CommonException(ExceptionCode.POST_NOT_EXIST_IN_COMMUNITY);}
        Article ret = community.get();
        ret.increaseViewCnt();
        List<Comment> comments = ret.getComments();
        List<CommentResponse.CommentReadResponse> dtoComs = new ArrayList<>();
        for(Comment com : comments){
            dtoComs.add(CommentResponse.CommentReadResponse.from(com));
        }
        return new ArticleResponse.ReadArticleWithComment().from(ret, dtoComs);
    }

    @Transactional(readOnly = true)
    public Page<ArticleResponse.ArticleReadResponse> readAllArticle(int page){
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        Pageable pageable = PageRequest.of(page, 7,sort);

        Page<Article> postPages = articleRepo.findAll(pageable);

        return postPages.map(post -> new ArticleResponse.ArticleReadResponse().from(post));
    }

    @Transactional
    public void deleteArticle(User user, Long postId){
        Optional<Article> req = articleRepo.findById(postId);
        if(req.isEmpty()){throw new CommonException(ExceptionCode.POST_NOT_EXIST_IN_COMMUNITY);}
        Article article = req.get();
        if(user.getMember().equals(ROLE.MANAGER) || Objects.equals(article.getUser().getId(), user.getId()))
            {
                articleRepo.delete(article);} else {
            throw new CommonException(ExceptionCode.NO_AUTHORIZATION_OF_DELETING_POST);
        }
    }
}
