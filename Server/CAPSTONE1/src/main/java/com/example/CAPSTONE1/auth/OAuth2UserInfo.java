//package com.example.CAPSTONE1.auth;
//
//import com.example.CAPSTONE1.user.entity.User;
//import jakarta.security.auth.message.AuthException;
//import lombok.Builder;
//
//import java.util.Map;
//
//@Builder
//public record OAuth2UserInfo(
//        String name,
//        String email,
//        String profile
//) {
//    public static OAuth2UserInfo of(String registrationId, Map<String, Object> attributes) {
//        return switch (registrationId) { // registration id별로 userInfo 생성
//            case "google" -> ofGoogle(attributes);
////            case "kakao" -> ofKakao(attributes);
//            default -> throw new AuthException(IllegalArgumentException);
//        };
//    }
//    private static OAuth2UserInfo ofGoogle(Map<String, Object> attributes) {
////        인증 성공하면 User에 관한값 추가해주기 위해
//        return OAuth2UserInfo.builder()
//                .name((String) attributes.get("name"))
//                .email((String) attributes.get("email"))
//                .profile((String) attributes.get("picture"))
//                .build();
//    }
//
////    private static OAuth2UserInfo ofKakao(Map<String, Object> attributes) {
////        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
////        Map<String, Object> profile = (Map<String, Object>) account.get("profile");
////
////        return OAuth2UserInfo.builder()
////                .name((String) profile.get("nickname"))
////                .email((String) account.get("email"))
////                .profile((String) profile.get("profile_image_url"))
////                .build();
////    }
//
//    public User toEntity() {
//        return User.builder()
//                .name(name)
//                .email(email)
//                .profile(profile)
////                .memberKey(KeyGenerator.generateKey())
////                .role(Role.USER)
//                .build();
//    }
//}
