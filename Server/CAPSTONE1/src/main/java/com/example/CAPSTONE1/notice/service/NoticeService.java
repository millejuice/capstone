package com.example.CAPSTONE1.notice.service;

import com.example.CAPSTONE1.common.exception.CommonException;
import com.example.CAPSTONE1.common.exception.ExceptionCode;
import com.example.CAPSTONE1.notice.dto.request.NoticeRequest;
import com.example.CAPSTONE1.notice.dto.response.NoticeResponse;
import com.example.CAPSTONE1.notice.entity.Notice;
import com.example.CAPSTONE1.notice.repo.NoticeRepo;
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
public class NoticeService {
    private final NoticeRepo noticeRepo;

    @Transactional
    public void createNotice(NoticeRequest.NoticeCreateRequest req,User user){
        if(!user.getMember().equals(ROLE.MANAGER)){throw new CommonException(ExceptionCode.NO_AUTHORIZATION_OF_CREATING_NOTICE);}
        Notice notice = Notice.from(req);
        noticeRepo.save(notice);
    }

    @Transactional(readOnly = true)
    public NoticeResponse.NoticeReadResponse readNotice(Long noticeId){
        Optional<Notice> req = noticeRepo.findById(noticeId);
        if(req.isEmpty()){throw new CommonException(ExceptionCode.NOTICE_NOT_EXIST);}
        Notice notice = req.get();
        notice.increaseViewCnt();
        return NoticeResponse.NoticeReadResponse.of(notice);
    }

    @Transactional(readOnly = true)
    public List<NoticeResponse.NoticeReadResponse> readAllNotice(){
        List<Notice> notices = noticeRepo.findAll();
        List<NoticeResponse.NoticeReadResponse> ret = new ArrayList<>();
        for(Notice notice : notices){
            ret.add(NoticeResponse.NoticeReadResponse.of(notice));
        }
        return ret;
    }

    @Transactional
    public void updatePin(User user, Long noticeId, NoticeRequest.NoticeCreateRequest upReq){
        if(!user.getMember().equals(ROLE.MANAGER)){throw new CommonException(ExceptionCode.NO_AUTHORIZATION_OF_UPDATING_NOTICE);}
        Optional<Notice> req = noticeRepo.findById(noticeId);
        if(req.isEmpty()){throw new CommonException(ExceptionCode.NOTICE_NOT_EXIST);}
        Notice notice = req.get();
        noticeRepo.save(Notice.updatePinned(noticeId,upReq, upReq.isPinned()));
    }

    @Transactional
    public void deleteNotice(Long noticeId, User user){
        if(!user.getMember().equals(ROLE.MANAGER)){throw new CommonException(ExceptionCode.NO_AUTHORIZATION_OF_DELETING_NOTICE);}
        Optional<Notice> req = noticeRepo.findById(noticeId);
        if(req.isEmpty()){throw new CommonException(ExceptionCode.NOTICE_NOT_EXIST);}
        Notice notice = req.get();
        noticeRepo.delete(notice);
    }
}
