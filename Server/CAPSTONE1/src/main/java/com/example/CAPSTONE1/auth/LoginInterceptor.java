package com.example.CAPSTONE1.auth;

import com.example.CAPSTONE1.auth.required.RequiredManagerLogin;
import com.example.CAPSTONE1.auth.required.RequiredNormalLogin;
import com.example.CAPSTONE1.auth.token.AuthorizationExtractor;
import com.example.CAPSTONE1.auth.token.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {
    private final AuthService authService;
    private final TokenProvider tokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // Preflight 요청은 그냥 통과시킴
        if (isPreflight(request)) {
            return true;
        }

        // handler가 HandlerMethod인지 확인
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            // 특정 어노테이션이 있는지 확인
            if (!(hasAnnotation(handlerMethod))) {
                return true;
            }

            // 토큰 추출
            String token = AuthorizationExtractor.extractAccessToken(request);

            // 어노테이션에 따라 토큰 검증
            if (handlerMethod.hasMethodAnnotation(RequiredNormalLogin.class)) {
                tokenProvider.validateBothToken(token);
            } else if (handlerMethod.hasMethodAnnotation(RequiredManagerLogin.class)) {
                authService.checkManagerByToken(token);
            }
        }

        // handler가 HandlerMethod가 아닌 경우에도 요청을 계속 진행
        return true;
    }


    private boolean hasAnnotation(HandlerMethod handlerMethod) {
        return handlerMethod.hasMethodAnnotation(RequiredNormalLogin.class)
                || handlerMethod.hasMethodAnnotation(RequiredManagerLogin.class);
    }

    private boolean isPreflight(HttpServletRequest request) {
        return HttpMethod.OPTIONS.matches(request.getMethod());
    }

}
