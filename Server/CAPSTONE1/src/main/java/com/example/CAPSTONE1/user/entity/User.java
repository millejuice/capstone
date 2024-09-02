package com.example.CAPSTONE1.user.entity;

import com.example.CAPSTONE1.community.entity.Community;
import com.example.CAPSTONE1.user.dto.request.UserRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
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

//    @OneToMany(targetEntity = Community.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy = "id", orphanRemoval = true)
//    private List<Community> communities;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime lastLogin;

    public static User of(UserRequest.CreateUserRequest req){
        return User.builder()
                .id(null)
                .name(req.getName())
                .email(req.getEmail())
                .member(ROLE.NORMAL)
                .nickname(req.getNickname())
                .lastLogin(LocalDateTime.now())
                .build();
//        return new User(null,req.getName(), req.getEmail(), ROLE.NORMAL, req.getNickname(), LocalDateTime.now());
    }

}
