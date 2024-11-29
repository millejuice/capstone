package com.example.CAPSTONE1.auth.oauth;

import com.example.CAPSTONE1.user.dto.request.UserRequest;
import com.example.CAPSTONE1.user.entity.ROLE;
import com.example.CAPSTONE1.user.entity.User;
import com.example.CAPSTONE1.user.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepo userRepo;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException{
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        log.info("oauth에서 받아온 정보 : "+ oAuth2User.getAttributes());

//        provide - google, kakao
        String provider = oAuth2UserRequest.getClientRegistration().getRegistrationId();
        log.info("provider : {}", provider);
            saveOrUpdate(oAuth2User);

        return super.loadUser(oAuth2UserRequest);
    }

    private void saveOrUpdate(OAuth2User attributes){
        String email = attributes.getAttribute("email");
        User user = userRepo.findByEmail(email);
        if(user==null){
            User u = User.of(new UserRequest.CreateUserRequest(attributes.getAttribute("name"),email,null));
            userRepo.save(u);
        } else {
            User u = new User(user.getId(), attributes.getAttribute("name"), email, user.getMember() == null ? ROLE.NORMAL : user.getMember(), user.getNickname(),LocalDateTime.now());
            userRepo.save(u);
        }
    }

    private void kakaoLogin(OAuth2User attributes){
        System.out.println(attributes.getAttributes());
    }
}
