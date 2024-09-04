package com.example.CAPSTONE1.comment.controller;

import com.example.CAPSTONE1.auth.login.NormalLogin;
import com.example.CAPSTONE1.auth.required.RequiredNormalLogin;
import com.example.CAPSTONE1.comment.dto.request.CommentRequest;
import com.example.CAPSTONE1.comment.service.CommentService;
import com.example.CAPSTONE1.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{articleId}/comment")
    @RequiredNormalLogin
    public void createComment(@NormalLogin User user, @PathVariable Long articleId, @RequestBody CommentRequest.WriteComment req){
        commentService.createComment(user,articleId,req);
    }

    @DeleteMapping("/comment/{commentId}")
    @RequiredNormalLogin
    public void deleteComment(@NormalLogin User user, @PathVariable Long commentId){
        commentService.deleteComment(user,commentId);
    }
}
