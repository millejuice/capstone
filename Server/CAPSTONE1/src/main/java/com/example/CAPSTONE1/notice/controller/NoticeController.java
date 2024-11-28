package com.example.CAPSTONE1.notice.controller;

import com.example.CAPSTONE1.auth.login.ManagerLogin;
import com.example.CAPSTONE1.auth.required.RequiredManagerLogin;
import com.example.CAPSTONE1.notice.dto.request.NoticeRequest;
import com.example.CAPSTONE1.notice.dto.response.NoticeResponse;
import com.example.CAPSTONE1.notice.service.NoticeService;
import com.example.CAPSTONE1.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {
    private final NoticeService noticeService;

    @PostMapping("")
    @RequiredManagerLogin
    public void createNotice(@ManagerLogin User user, @RequestBody NoticeRequest.NoticeCreateRequest req){
        noticeService.createNotice(req,user);
    }

    @GetMapping("/{noticeId}")
    public ResponseEntity<NoticeResponse.NoticeReadResponse> readNotice(@PathVariable Long noticeId){
        return ResponseEntity.ok(noticeService.readNotice(noticeId));
    }

    @PatchMapping("/{noticeId}")
    @RequiredManagerLogin
    public void updatePine(@PathVariable Long noticeId, @RequestBody NoticeRequest.NoticeCreateRequest req,@ManagerLogin User user){
        noticeService.updatePin(user,noticeId,req);
    }

    @RequiredManagerLogin
    @DeleteMapping("/{noticeId}")
    public void deleteNotice(@PathVariable Long noticeId,@ManagerLogin User user){
        noticeService.deleteNotice(noticeId,user);
    }
}
