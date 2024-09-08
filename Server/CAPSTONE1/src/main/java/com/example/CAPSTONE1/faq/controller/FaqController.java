package com.example.CAPSTONE1.faq.controller;

import com.example.CAPSTONE1.auth.login.ManagerLogin;
import com.example.CAPSTONE1.auth.required.RequiredManagerLogin;
import com.example.CAPSTONE1.faq.dto.request.FAQRequest;
import com.example.CAPSTONE1.faq.service.FAQService;
import com.example.CAPSTONE1.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/faq")
public class FaqController {
    private final FAQService faqService;

    @PostMapping("")
    public void createQuestion(@RequestBody FAQRequest.QuestionCreateRequest req){
        faqService.createQuestion(req);
    }

    @PatchMapping("/{faqId}")
    @RequiredManagerLogin
    public void createAnswer(@ManagerLogin User user, @PathVariable Long faqId, @RequestBody FAQRequest.AnswerCreateRequest req){
        faqService.createAnswer(faqId,req);
    }
}
