package com.example.CAPSTONE1.auth;

import com.example.CAPSTONE1.user.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException{
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        log.info("oauth에서 받아온 정보 : "+ oAuth2User.getAttributes());
        return super.loadUser(oAuth2UserRequest);
    }
}
