package com.example.CAPSTONE1.auth;

import com.example.CAPSTONE1.exception.CommonException;
import com.example.CAPSTONE1.exception.ExceptionCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

/**
 * 토큰 생성, 토큰 파서, 토큰 유효성 검사 등 토큰 관련 로직을 담고 있는 객체
 */

@Component
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class TokenProvider {
    @Value("${jwt.normal.secret")
    private String normalSecretKey;

    @Value(("${jwt.manager.secret"))
    private String managerSecretKey;

    @Value("${jwt.expiration_time}")
    private String expiredTime;

    public String createToken(String email,Member member ) {
        Date now = new Date();
        long expiredT = Long.parseLong(expiredTime);
        Date expiredDay = new Date(now.getTime() + expiredT);
        Claims claims = Jwts.claims().setSubject(email);

        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        return Jwts.builder()
                .setIssuedAt(now)
                .setIssuer("Capstone")
                .setExpiration(expiredDay)
                .setClaims(claims)
                .signWith(key)
                .compact();

    }

    public String getSecretKey(Member member){
        if(Member.isNormal(member)) return Base64.getEncoder().encodeToString(normalSecretKey.getBytes());
        return Base64.getEncoder().encodeToString(managerSecretKey.getBytes());
    }

    public boolean validateBothToken(String token){

        try{
            validateToken(token, Member.NORMAL);
            return true;
        } catch (CommonException | JwtException | IllegalArgumentException e){
            try{
                validateToken(token, Member.MANAGER);
                return true;
            } catch (JwtException | IllegalArgumentException e2){
                return false;
            }
        }
    }

    public void validateToken(String token, Member member) {
        try {
            JwtParser jwtParser = getJwtParser(member);
            jwtParser.parseClaimsJws(token);
        } catch (JwtException | IllegalArgumentException e) {
            throw new CommonException(ExceptionCode.INVALID_TOKEN_EXCEPTION);
        }
    }

    public String getPayLoad(String token, Member member) {
        JwtParser jwtParser = getJwtParser(member);
        return jwtParser.parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private JwtParser getJwtParser(Member member) {
        return Jwts.parser()
                .setSigningKey(getSecretKey(member));
    }

    public boolean isValidToken(String token, Member member) {
        try {
            validateToken(token, member);
            return true;
        } catch (CommonException e) {
            return false;
        }
    }
}
