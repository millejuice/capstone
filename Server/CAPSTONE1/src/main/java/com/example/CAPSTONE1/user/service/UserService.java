package com.example.CAPSTONE1.user.service;

import com.example.CAPSTONE1.common.exception.CommonException;
import com.example.CAPSTONE1.common.exception.ExceptionCode;
import com.example.CAPSTONE1.user.dto.request.UserRequest;
import com.example.CAPSTONE1.user.entity.User;
import com.example.CAPSTONE1.user.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    public void createUser(UserRequest.CreateUserRequest req){
        userRepo.save(User.of(req));
    }

    public User readUser(Long userId){
        return userRepo.findById(userId).orElseThrow(() -> new CommonException(ExceptionCode.USER_NOT_FOUND));
    }
}
