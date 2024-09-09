package com.example.CAPSTONE1.faq.controller;

import com.example.CAPSTONE1.auth.login.ManagerLogin;
import com.example.CAPSTONE1.auth.login.NormalLogin;
import com.example.CAPSTONE1.auth.required.RequiredManagerLogin;
import com.example.CAPSTONE1.auth.required.RequiredNormalLogin;
import com.example.CAPSTONE1.faq.dto.request.FAQRequest;
import com.example.CAPSTONE1.faq.dto.response.FAQResponse;
import com.example.CAPSTONE1.faq.service.FAQService;
import com.example.CAPSTONE1.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/faq")
public class FaqController {
    private final FAQService faqService;

    @PostMapping("")
    @RequiredNormalLogin
    public void createQuestion(@NormalLogin User user,@RequestBody FAQRequest.QuestionCreateRequest req){
        faqService.createQuestion(user,req);
    }

    @PatchMapping("/{faqId}")
    @RequiredManagerLogin
    public void createAnswer(@ManagerLogin User user, @PathVariable Long faqId, @RequestBody FAQRequest.AnswerCreateRequest req){
        faqService.createAnswer(faqId,req);
    }

    @GetMapping("/{faqId}")
    public ResponseEntity<FAQResponse.ReadFAQResponse> readFAQ(@PathVariable Long faqId){
        return ResponseEntity.ok(faqService.readFAQ(faqId));
    }

    @GetMapping("")
    public ResponseEntity<List<FAQResponse.ReadFAQResponse>> readAllFaq(){
        return ResponseEntity.ok().body(faqService.readAllFaq());
    }

    @DeleteMapping("/{faqId}")
    @RequiredNormalLogin
    public void deleteFaq(@NormalLogin User user, @PathVariable Long faqId){
        faqService.deleteFaq(user,faqId);
    }
}
