package com.suiteonix.nix.kernel.security.jwt;

import com.suiteonix.nix.Common.JwtProperties;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Getter
@Service
@RequiredArgsConstructor
class JwtPropertiesImpl implements JwtProperties {

    @Value("${app.jwt.secret:tnlRl41pPvteCEClg2GKVsE0bTnldPpuqGlAPFUqh36kbGO6gCuYfTHb54jDaxkGMnqlQQ}")
    String secret; // Default should be overridden in properties
    @Value("${app.jwt.expirationMs:86400000000}")
    long expirationMs = 86400000000L; // 1000 day in milliseconds (default)
    @Value("${app.jwt.expirationMs:86400000}")
    long refreshTokenExpirationMs; // 1 day in milliseconds (default)
    @Value("${app.jwt.emailVerificationTTL:86400000}")
    long emailVerificationTTL = 86400000; // 20 minutes in milliseconds (default)
    @Value("${app.jwt.issuer:suiteonix}")
    String issuer;

    @Bean
    public Key getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
