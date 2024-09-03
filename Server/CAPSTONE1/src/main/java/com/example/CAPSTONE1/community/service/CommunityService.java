package com.example.CAPSTONE1.community.service;

import com.example.CAPSTONE1.common.exception.CommonException;
import com.example.CAPSTONE1.common.exception.ExceptionCode;
import com.example.CAPSTONE1.common.pagination.PaginationRequest;
import com.example.CAPSTONE1.community.dto.request.CommunityRequest;
import com.example.CAPSTONE1.community.dto.response.CommunityResponse;
import com.example.CAPSTONE1.community.entity.Community;
import com.example.CAPSTONE1.community.repo.CommunityRepo;
import com.example.CAPSTONE1.user.entity.ROLE;
import com.example.CAPSTONE1.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityRepo communityRepo;

    @Transactional
    public void createPost(User user, CommunityRequest.PostCreateRequest req){
        communityRepo.save(Community.from(user, req.getTitle(), req.getContent()));
    }

    @Transactional
    public void updatePost(User user, CommunityRequest.PostUpdateRequest req,Long postId){
        Optional<Community> community = communityRepo.findById(postId);
        if(community.isEmpty()){throw new CommonException(ExceptionCode.POST_NOT_EXIST_IN_COMMUNITY);}
        if(!Objects.equals(community.get().getUser().getId(), user.getId())){throw new CommonException(ExceptionCode.USER_ID_NOT_EQUAL);}
        Community entity = community.get();
        entity.setTitle(req.getTitle());
        entity.setContent(req.getContent());
        communityRepo.save(entity);
    }

    @Transactional
    public CommunityResponse.ReadPostResponse readPost(Long postId){
        Optional<Community> community = communityRepo.findById(postId);
        if(community.isEmpty()){throw new CommonException(ExceptionCode.POST_NOT_EXIST_IN_COMMUNITY);}
        Community ret = community.get();
        ret.increaseViewCnt();
        return new CommunityResponse.ReadPostResponse().from(ret);
    }

    @Transactional(readOnly = true)
    public Page<CommunityResponse.ReadPostResponse> readAllPost(int page){
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        Pageable pageable = PageRequest.of(page, 7,sort);

        Page<Community> postPages = communityRepo.findAll(pageable);

        return postPages.map(post -> new CommunityResponse.ReadPostResponse().from(post));
    }

    @Transactional
    public void deletePost(User user, Long postId){
        Optional<Community> req = communityRepo.findById(postId);
        if(req.isEmpty()){throw new CommonException(ExceptionCode.POST_NOT_EXIST_IN_COMMUNITY);}
        Community community = req.get();
        if(user.getMember().equals(ROLE.MANAGER) || Objects.equals(community.getUser().getId(), user.getId()))
            {communityRepo.delete(community);} else {
            throw new CommonException(ExceptionCode.NO_AUTHORIZATION_OF_DELETING_POST);
        }
    }
}
