package com.example.CAPSTONE1.auth.oauth;

import com.example.CAPSTONE1.auth.token.TokenProvider;
import com.example.CAPSTONE1.user.entity.User;
import com.example.CAPSTONE1.user.repo.UserRepo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class Oauth2SuccessHandler implements AuthenticationSuccessHandler {
    private final TokenProvider tokenProvider;
    private final UserRepo userRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 로그인 성공 후 accessToken, refreshToken 발급
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        User u = userRepo.findByEmail(email);
        System.out.println(u.getId());
//        유저의 id, role로 token 생성
        String token = tokenProvider.createToken(String.valueOf(u.getId()), u.getMember());

        /**
         * domain : 특정 도메인에서만
         * httpOnly : 브라우저에서 쿠키 접근 막음
         * secure : https로만 접근하냐?
         * sameSite : 서드파티요청에 쿠키를 전달할지 설정
         */
        ResponseCookie responseCookie = ResponseCookie.from("Capstone",token)
                .domain("localhost")
                .path("/")
                .httpOnly(true)
                .secure(false)
                .maxAge(Duration.ofDays(30))
                .sameSite("Strict")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());

        // Redirect to the chat page
        response.sendRedirect("http://localhost:3000/chat");
    }
}
