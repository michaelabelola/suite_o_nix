package com.suiteonix.nix.Auth.internal.domain.services;

import com.suiteonix.nix.Common.JwtProperties;
import com.suiteonix.nix.shared.ids.NixID;
import com.suiteonix.nix.shared.ids.NixRole;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AuthModuleJwtUtil {
    private final JwtProperties jwtProperties;

    public String generateAccessToken(NixID principalId, NixRole principalRole, NixID actorId, NixRole actorRole) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(principalId.toString())
                .claim(JwtProperties.ROLE, principalRole.name())
                .claim(JwtProperties.ACTOR_ID, actorId.toString())
                .claim(JwtProperties.ACTOR_ROLE, actorRole.name())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + jwtProperties.getExpirationMs()))
                .setIssuer(jwtProperties.getIssuer())
                .signWith(jwtProperties.getSigningKey())
                .compact();
    }

    public String generateRefreshToken(NixID principalId, NixRole principalRole, NixID actorId, NixRole actorRole) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(principalId.toString())
                .claim(JwtProperties.ROLE, principalRole.name())
                .claim(JwtProperties.ACTOR_ID, actorId.toString())
                .claim(JwtProperties.ACTOR_ROLE, actorRole.name())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + jwtProperties.getRefreshTokenExpirationMs()))
                .setIssuer(jwtProperties.getIssuer())
                .signWith(jwtProperties.getSigningKey())
                .compact();
    }

}
