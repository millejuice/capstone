package com.example.CAPSTONE1.auth;

import com.example.CAPSTONE1.auth.login.NormalLogin;
import com.example.CAPSTONE1.auth.token.AuthorizationExtractor;
import com.example.CAPSTONE1.common.exception.CommonException;
import com.example.CAPSTONE1.common.exception.ExceptionCode;
import com.example.CAPSTONE1.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class NormalLoginArgumentResolver implements HandlerMethodArgumentResolver {
    private final AuthService authService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(NormalLogin.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String accessToken = AuthorizationExtractor
                .extractAccessToken(Objects.requireNonNull(webRequest.getNativeRequest(HttpServletRequest.class)));
        User user = authService.getLoginNormal(accessToken);
        if(user == null){
            user = authService.getLoginManager(accessToken);
        }
        if(user == null){
            throw new CommonException(ExceptionCode.USER_NOT_FOUND);
        }
        return user;
    }
}
