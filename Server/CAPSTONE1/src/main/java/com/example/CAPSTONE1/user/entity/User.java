package com.example.CAPSTONE1.user.entity;

import com.example.CAPSTONE1.user.dto.request.UserRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private ROLE member;

    private String nickname;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime lastLogin;

    public static User of(UserRequest.CreateUserRequest req){
        return new User(null,req.getName(), req.getEmail(), ROLE.NORMAL, req.getNickname(), LocalDateTime.now());
    }
}
