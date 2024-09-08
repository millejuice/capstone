package com.example.CAPSTONE1.faq.service;

import com.example.CAPSTONE1.common.exception.CommonException;
import com.example.CAPSTONE1.common.exception.ExceptionCode;
import com.example.CAPSTONE1.faq.dto.request.FAQRequest;
import com.example.CAPSTONE1.faq.entity.FAQ;
import com.example.CAPSTONE1.faq.repo.FAQRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FAQService {
    private final FAQRepo faqRepo;

    @Transactional
    public void createQuestion(FAQRequest.QuestionCreateRequest req){
        FAQ faq = FAQ.createQuestion(req.getTitle(), req.getContent());
        faqRepo.save(faq);
    }

    @Transactional
    public void createAnswer(Long faqId, FAQRequest.AnswerCreateRequest req){
        Optional<FAQ> f = faqRepo.findById(faqId);
        if(f.isEmpty()){throw new CommonException(ExceptionCode.FAQ_NOT_EXIST);}
        FAQ reqFaq = f.get();
        FAQ faq = FAQ.createAnswer(reqFaq, reqFaq.getTitle(), reqFaq.getContent());
        faqRepo.save(faq);
    }

    public void readFAQ(){

    }
}
