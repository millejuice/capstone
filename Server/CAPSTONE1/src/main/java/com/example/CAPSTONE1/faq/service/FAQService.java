package com.example.CAPSTONE1.faq.service;

import com.example.CAPSTONE1.common.exception.CommonException;
import com.example.CAPSTONE1.common.exception.ExceptionCode;
import com.example.CAPSTONE1.faq.dto.request.FAQRequest;
import com.example.CAPSTONE1.faq.dto.response.FAQResponse;
import com.example.CAPSTONE1.faq.entity.FAQ;
import com.example.CAPSTONE1.faq.repo.FAQRepo;
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
public class FAQService {
    private final FAQRepo faqRepo;

    @Transactional
    public void createQuestion(User user,FAQRequest.QuestionCreateRequest req){
        FAQ faq = FAQ.createQuestion(req.getTitle(), req.getContent(),user);
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

    @Transactional(readOnly = true)
    public FAQResponse.ReadFAQResponse readFAQ(Long faqId){
        Optional<FAQ> req = faqRepo.findById(faqId);
        if(req.isEmpty()){throw new CommonException(ExceptionCode.FAQ_NOT_EXIST);}
        FAQ faq = req.get();
        return FAQResponse.ReadFAQResponse.from(faq);
    }

    @Transactional(readOnly = true)
    public List<FAQResponse.ReadFAQResponse> readAllFaq(){
        List<FAQ> faqs = faqRepo.findAll();
        List<FAQResponse.ReadFAQResponse> ret = new ArrayList<>();
        for(FAQ faq : faqs){
            ret.add(FAQResponse.ReadFAQResponse.from(faq));
        }
        return ret;
    }

    @Transactional
    public void deleteFaq(User user, Long faqId){
        Optional<FAQ> req = faqRepo.findById(faqId);
        if(req.isEmpty()){throw new CommonException(ExceptionCode.FAQ_NOT_EXIST);}
        FAQ faq = req.get();
        if(user.getId().equals(faq.getUser().getId()) || user.getMember().equals(ROLE.MANAGER)) {
            faqRepo.delete(faq);
        } else {
            throw new CommonException(ExceptionCode.NO_AUTHORIZATION_OF_DELETING_FAQ);
        }
    }
}
