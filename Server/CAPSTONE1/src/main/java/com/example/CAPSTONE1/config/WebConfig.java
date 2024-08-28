package com.example.CAPSTONE1.config;

import com.example.CAPSTONE1.auth.LoginInterceptor;
import com.example.CAPSTONE1.auth.ManagerLoginArgumentResolver;
import com.example.CAPSTONE1.auth.NormalLoginArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final LoginInterceptor loginInterceptor;
    private final NormalLoginArgumentResolver normalLoginArgumentResolver;
    private final ManagerLoginArgumentResolver managerLoginArgumentResolver;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("Authorization", "Content-Type")
                .exposedHeaders("Custom-Header")
                .maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers){
        resolvers.add(normalLoginArgumentResolver);
        resolvers.add(managerLoginArgumentResolver);
    }
}
