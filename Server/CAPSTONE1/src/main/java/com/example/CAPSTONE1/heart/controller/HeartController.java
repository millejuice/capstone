package com.example.CAPSTONE1.heart.controller;

import com.example.CAPSTONE1.auth.login.NormalLogin;
import com.example.CAPSTONE1.auth.required.RequiredNormalLogin;
import com.example.CAPSTONE1.heart.service.HeartService;
import com.example.CAPSTONE1.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class HeartController {
    private final HeartService heartService;

    @PatchMapping("/{articleId}/like")
    @RequiredNormalLogin
    public void updateLike(@NormalLogin User user, @PathVariable Long articleId){
        heartService.updateLike(user,articleId);
    }
}
