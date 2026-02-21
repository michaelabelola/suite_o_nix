package com.suiteonix.nix.Auth.internal.infrastructure;

import com.suiteonix.nix.Common.JwtProperties;
import com.suiteonix.nix.shared.exceptions.EX;
import com.suiteonix.nix.shared.ids.NixID;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MailJwtUtil {
    private final JwtProperties jwtProperties;

    public String generateEmailVerificationToken(NixID userId, long ttlMs) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + ttlMs);
        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("type", "EMAIL_VERIFICATION")
                .setIssuedAt(now)
                .setExpiration(expiry)
                .setIssuer(jwtProperties.getIssuer())
                .signWith(jwtProperties.getSigningKey())
                .compact();
    }
    public Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(jwtProperties.getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SecurityException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw EX.forbidden("INVALID_AUTHENTICATION_TOKEN", "Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw EX.forbidden("EXPIRED_AUTHENTICATION_TOKEN", "Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw EX.forbidden("UNSUPPORTED_AUTHENTICATION_TOKEN", "Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw EX.forbidden("AUTHENTICATION_EMPTY_TOKEN", "No claims found in authentication token");
        }
        return null;
    }
}
