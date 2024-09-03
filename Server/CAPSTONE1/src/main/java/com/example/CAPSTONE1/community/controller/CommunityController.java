package com.example.CAPSTONE1.community.controller;

import com.example.CAPSTONE1.auth.login.ManagerLogin;
import com.example.CAPSTONE1.auth.login.NormalLogin;
import com.example.CAPSTONE1.auth.required.RequiredManagerLogin;
import com.example.CAPSTONE1.auth.required.RequiredNormalLogin;
import com.example.CAPSTONE1.common.pagination.PaginationRequest;
import com.example.CAPSTONE1.community.dto.request.CommunityRequest;
import com.example.CAPSTONE1.community.dto.response.CommunityResponse;
import com.example.CAPSTONE1.community.entity.Community;
import com.example.CAPSTONE1.community.service.CommunityService;
import com.example.CAPSTONE1.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {
    private final CommunityService communityService;

    @PostMapping("")
    @RequiredNormalLogin
    public void createPost(@NormalLogin User user, @RequestBody CommunityRequest.PostCreateRequest req){
        communityService.createPost(user,req);
    }

    @PatchMapping("/{postId}")
    @RequiredNormalLogin
    public void updatePost(@NormalLogin User user, @RequestBody CommunityRequest.PostUpdateRequest req,@PathVariable Long postId){
        communityService.updatePost(user,req,postId);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<CommunityResponse.ReadPostResponse> readPost(@PathVariable Long postId){
        return ResponseEntity.ok(communityService.readPost(postId));
    }

    @GetMapping("")
    public ResponseEntity<Page<CommunityResponse.ReadPostResponse>> findAllPost(@RequestParam int page){
        return ResponseEntity.ok(communityService.readAllPost(page));
    }

    @DeleteMapping("/{postId}")
    @RequiredNormalLogin
    public void deletePost(@NormalLogin User user, @PathVariable Long postId){
        communityService.deletePost(user,postId);
    }
}
