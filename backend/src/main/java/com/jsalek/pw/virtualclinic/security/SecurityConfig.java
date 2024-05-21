package com.jsalek.pw.virtualclinic.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;

@Configuration
public class SecurityConfig {

    @Bean
    public AntPathMatcher pathPattern() {
        return new AntPathMatcher();
    }

}
