package com.example.newsfeed.jwt.config;

import com.example.newsfeed.jwt.resolver.UserSessionArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class JwtWebConfig implements WebMvcConfigurer {

    private final UserSessionArgumentResolver userSessionArgumentResolver;

    public JwtWebConfig(UserSessionArgumentResolver userSessionArgumentResolver) {
        this.userSessionArgumentResolver = userSessionArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userSessionArgumentResolver);
    }
}