package com.example.CAPSTONE1.faq.entity;

import com.example.CAPSTONE1.common.BaseTimeEntity;
import com.example.CAPSTONE1.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FAQ extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String answerTitle;
    private String answerContent;
    private Boolean answered;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "USER_ID")
    private User user;

    public static FAQ createQuestion(String title, String content,User user){
        return FAQ.builder()
                .id(null)
                .title(title)
                .content(content)
                .user(user)
                .build();
    }

    public static FAQ createAnswer(FAQ faq,String aTitle, String aContent){
        return FAQ.builder()
                .id(faq.id)
                .title(faq.getTitle())
                .content(faq.getContent())
                .answerTitle(aTitle)
                .answerContent(aContent)
                .answered(true)
                .build();
    }
}
