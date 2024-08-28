package com.example.CAPSTONE1.auth.token;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Enumeration;
import java.util.Locale;

/**
 * HTTPRequest에서 Authorization Header 추출하고, Bearer Type의 Access Token 추출
 * http request 중에서 header들만 뽑아서
 * Bearer로 시작하는 헤더 찾아서 자르고 토큰값 돌려주고 없으면 빈값 리턴
 *
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthorizationExtractor {
    public static final String AUTHENTICATION_TYPE = "Bearer";
    public static final String AUTHORIZATION = "Authorization";

    public static String extractAccessToken(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders(AUTHORIZATION);
        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            if (value.toLowerCase(Locale.ROOT).startsWith(AUTHENTICATION_TYPE.toLowerCase(Locale.ROOT))) {
                return value.split(" ")[1];
            }
        }
        return "";
    }

}
