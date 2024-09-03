package com.example.CAPSTONE1.user.controller;

import com.example.CAPSTONE1.auth.required.RequiredManagerLogin;
import com.example.CAPSTONE1.auth.required.RequiredNormalLogin;
import com.example.CAPSTONE1.user.dto.request.UserRequest;
import com.example.CAPSTONE1.user.entity.User;
import com.example.CAPSTONE1.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("")
    public void createUser(@RequestBody UserRequest.CreateUserRequest req){
        userService.createUser(req);
    }

    @GetMapping("/{userId}")
    public User readUser(@PathVariable Long userId){
        return userService.readUser(userId);
    }
}
