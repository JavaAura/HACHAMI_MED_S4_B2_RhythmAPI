package com.yc.Rhythm.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Validated
@Configuration
@ConfigurationProperties(prefix = "rhythm.app")
public class JwtProperties {

    @NotBlank(message = "JWT secret cannot be empty")
    private String jwtSecret;

    @Positive(message = "JWT expiration must be positive")
    private int jwtExpirationMs = 86400000;

    public String getJwtSecret() {
        return jwtSecret;
    }

    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public int getJwtExpirationMs() {
        return jwtExpirationMs;
    }

    public void setJwtExpirationMs(int jwtExpirationMs) {
        this.jwtExpirationMs = jwtExpirationMs;
    }
}

