package com.example.CAPSTONE1.user.service;

import com.example.CAPSTONE1.user.entity.User;
import com.example.CAPSTONE1.user.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    public void createUser(String name){
        userRepo.save(User.of(name));
    }

    public User readUser(Long userId){
        Optional<User> u = userRepo.findById(userId);
        return u.get();
    }
}
