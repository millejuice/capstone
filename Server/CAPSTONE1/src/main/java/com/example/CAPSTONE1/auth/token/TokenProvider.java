package com.example.CAPSTONE1.auth.token;

import com.example.CAPSTONE1.user.entity.ROLE;
import com.example.CAPSTONE1.common.exception.CommonException;
import com.example.CAPSTONE1.common.exception.ExceptionCode;
import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
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

    public String createToken(String payLoad, ROLE member ) {
        Date now = new Date();
        long expiredT = Long.parseLong(expiredTime);
        Date expiredDay = new Date(now.getTime() + expiredT);
        Claims claims = Jwts.claims().setSubject(payLoad); //user의 id로 claim 만듦

//        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(getSecretKey(member)));

        return Jwts.builder()
                .setIssuedAt(now)
                .setIssuer("Capstone")
                .setExpiration(expiredDay)
                .setClaims(claims)
                .signWith(getSecretKey(member),SignatureAlgorithm.HS256)
                .compact();

    }

    public SecretKey getSecretKey(ROLE member) {
        byte[] keyBytes;
        if (ROLE.isNormal(member)) {
            keyBytes = Arrays.copyOf(normalSecretKey.getBytes(StandardCharsets.UTF_8), 32);
        } else {
            keyBytes = Arrays.copyOf(managerSecretKey.getBytes(StandardCharsets.UTF_8), 32);
        }
        return new SecretKeySpec(keyBytes, "HmacSHA256");
    }


    public boolean validateBothToken(String token){

        try{
            validateToken(token, ROLE.NORMAL);
            return true;
        } catch (CommonException | JwtException | IllegalArgumentException e){
            try{
                validateToken(token, ROLE.MANAGER);
                return true;
            } catch (JwtException | IllegalArgumentException e2){
                return false;
            }
        }
    }

    public void validateToken(String token, ROLE member) {
        try {
            JwtParser jwtParser = getJwtParser(member);
            jwtParser.parseClaimsJws(token);
        } catch (JwtException | IllegalArgumentException e) {
            throw new CommonException(ExceptionCode.INVALID_TOKEN_EXCEPTION);
        }
    }

    public String getPayLoad(String token, ROLE member) {
        JwtParser jwtParser = getJwtParser(member);
        return jwtParser.parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private JwtParser getJwtParser(ROLE member) {
        return Jwts.parser()
                .setSigningKey(getSecretKey(member));
    }

    public boolean isValidToken(String token, ROLE member) {
        try {
            validateToken(token, member);
            return true;
        } catch (CommonException e) {
            return false;
        }
    }
}
