package com.example.CAPSTONE1.comment.service;

import com.example.CAPSTONE1.comment.dto.request.CommentRequest;
import com.example.CAPSTONE1.comment.dto.response.CommentResponse;
import com.example.CAPSTONE1.comment.entity.Comment;
import com.example.CAPSTONE1.comment.repo.CommentRepo;
import com.example.CAPSTONE1.common.exception.CommonException;
import com.example.CAPSTONE1.common.exception.ExceptionCode;
import com.example.CAPSTONE1.article.entity.Article;
import com.example.CAPSTONE1.article.repo.ArticleRepo;
import com.example.CAPSTONE1.user.entity.ROLE;
import com.example.CAPSTONE1.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepo commentRepo;
    private final ArticleRepo articleRepo;

    public List<CommentResponse.CommentReadResponse> readComment(Long postId){
        Optional<Article> opCommunity = articleRepo.findById(postId);
        if(opCommunity.isEmpty()){throw new CommonException(ExceptionCode.POST_NOT_EXIST_IN_COMMUNITY);}
        List<Comment> comments = opCommunity.get().getComments();
        List<CommentResponse.CommentReadResponse> ret = new ArrayList<>();
        for (Comment comment : comments) {
            ret.add(CommentResponse.CommentReadResponse.from(comment));
        }
        return ret;
    }

    @Transactional
    public void createComment(User user, Long articleId,CommentRequest.WriteComment req){
        Optional<Article> opArc = articleRepo.findById(articleId);
        if(opArc.isEmpty()){throw new CommonException(ExceptionCode.POST_NOT_EXIST_IN_COMMUNITY);}
        Comment ret = Comment.from(user, req);
        Article arc = opArc.get();
        arc.getComments().add(ret);
        articleRepo.save(arc);
    }

    @Transactional
    public void deleteComment(User user, Long commentId){
        Optional<Comment> opCom = commentRepo.findById(commentId);
        if(opCom.isEmpty()){throw new CommonException(ExceptionCode.COMMENT_NOT_EXIST);}
        Comment comment = opCom.get();
        if(user.getId().equals(comment.getUser().getId()) || user.getMember().equals(ROLE.MANAGER)){
            commentRepo.deleteById(commentId);
        } else{
            throw new CommonException(ExceptionCode.NO_AUTHORIZATION_OF_DELETING_COMMENT);
        }
    }
}
