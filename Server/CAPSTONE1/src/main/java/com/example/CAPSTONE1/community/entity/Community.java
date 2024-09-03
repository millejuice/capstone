package com.example.CAPSTONE1.community.entity;

import com.example.CAPSTONE1.comment.entity.Comment;
import com.example.CAPSTONE1.common.BaseTimeEntity;
import com.example.CAPSTONE1.community.dto.request.CommunityRequest;
import com.example.CAPSTONE1.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CascadeType;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Community extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 10000)
    private String content;

    private int viewCnt;
    public void increaseViewCnt(){this.viewCnt++;}

    private int likeCnt;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    public static Community from(User user,String title, String content){
        return Community.builder()
                .id(null)
                .title(title)
                .content(content)
                .user(user)
                .build();
    }

}
