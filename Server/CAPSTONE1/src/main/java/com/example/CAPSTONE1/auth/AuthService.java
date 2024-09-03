package com.example.CAPSTONE1.auth;

import com.example.CAPSTONE1.auth.token.TokenProvider;
import com.example.CAPSTONE1.common.exception.CommonException;
import com.example.CAPSTONE1.common.exception.ExceptionCode;
import com.example.CAPSTONE1.user.entity.ROLE;
import com.example.CAPSTONE1.user.entity.User;
import com.example.CAPSTONE1.user.repo.UserRepo;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenProvider tokenProvider;
    private final UserRepo userRepo;

    @Transactional(readOnly = true)
    public User findNormalUserByToken(String token){
        String payLoad;
        if(!tokenProvider.validateBothToken(token)){
            throw new CommonException(ExceptionCode.INVALID_TOKEN_EXCEPTION);
        }
        try{
            payLoad = tokenProvider.getPayLoad(token, ROLE.NORMAL);
            if(payLoad.isEmpty()){
                throw new CommonException(ExceptionCode.EMPTY_PAYLOAD_IN_TOKEN);
            }
        } catch (SignatureException e){
            try {
                payLoad = tokenProvider.getPayLoad(token, ROLE.MANAGER);
                if(payLoad.isEmpty()){
                    throw new CommonException(ExceptionCode.EMPTY_PAYLOAD_IN_TOKEN);
                }
            } catch (JwtException | IllegalArgumentException e2){
                throw new CommonException(ExceptionCode.INVALID_TOKEN_EXCEPTION);
            }
        }
        Long id = Long.parseLong(payLoad);
        Optional<User> user = userRepo.findById(id);

        if(user.isPresent()){
            return user.get();
        } else {
            throw new CommonException(ExceptionCode.USER_NOT_FOUND);
        }
    }

    @Transactional(readOnly = true)
    public User findManagerByToken(String token){
        if(!tokenProvider.isValidToken(token,ROLE.MANAGER)){
            throw new CommonException(ExceptionCode.UNAUTHORIZED_MANAGER_TOKEN);
        }
//        payLoad에는 user의 id, ROLE이 들어있음
        String payLoad = tokenProvider.getPayLoad(token, ROLE.MANAGER);
        if(payLoad.isEmpty()){
            throw new CommonException(ExceptionCode.EMPTY_PAYLOAD_IN_TOKEN);
        }
        Long id = Long.parseLong(payLoad);
        Optional<User> user = userRepo.findById(id);
        if(user.isPresent()){
            if(user.get().getMember().equals(ROLE.MANAGER)){
                return user.get();
            } else {
                throw new CommonException(ExceptionCode.UNAUTHORIZED_MANAGER_TOKEN);
            }
        } else {
            throw new CommonException(ExceptionCode.USER_NOT_FOUND);
        }
    }

    @Transactional(readOnly = true)
    public User getLoginNormal(String accessToken){
        //        if(!user.getMember().equals(ROLE.NORMAL)){
//            throw new CommonException(ExceptionCode.USER_NOT_FOUND);
//        }
        return findNormalUserByToken(accessToken);
    }

    @Transactional(readOnly = true)
    public User getLoginManager(String accessToken) {
        User user = findManagerByToken(accessToken);
        if(!user.getMember().equals(ROLE.MANAGER))
            throw new CommonException(ExceptionCode.UNAUTHORIZED_MANAGER_TOKEN);
        return user;
    }

    @Transactional(readOnly = true)
    public void checkManagerByToken(String token) {
        User user = findManagerByToken(token);
        if (!user.getMember().equals(ROLE.MANAGER))
            throw new CommonException(ExceptionCode.UNAUTHORIZED_MANAGER_TOKEN);
    }
}
