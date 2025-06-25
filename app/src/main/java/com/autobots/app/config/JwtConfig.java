package com.autobots.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.autobots.app.utils.JwtUtil;

@Configuration
public class JwtConfig {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms}")
    private long expirationMs;

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil(secret, expirationMs);
    }
}
