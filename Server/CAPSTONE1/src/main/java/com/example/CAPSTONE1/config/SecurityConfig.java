package com.example.CAPSTONE1.config;

import com.example.CAPSTONE1.auth.CustomOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final CustomOauth2UserService customOauth2UserService;
    private final CorsConfig corsConfig;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable);
        http.addFilter(corsConfig.corsFilter());
        http.authorizeHttpRequests(au -> au.anyRequest().permitAll());
        http.oauth2Login(
                oauth->oauth.loginPage("http://localhost:3000/")
                        .defaultSuccessUrl("http://localhost:3000/chat", true)
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOauth2UserService))
        );
        return http.build();
    }
}
