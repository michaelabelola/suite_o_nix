package com.suiteonix.nix.kernel.security;

import com.suiteonix.nix.kernel.security.jwt.JwtProperties;
import com.suiteonix.nix.shared.ids.NixID;
import com.suiteonix.nix.shared.ids.NixRole;
import com.suiteonix.nix.shared.principal.Actor;
import com.suiteonix.nix.shared.principal.Principal;
import io.jsonwebtoken.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AuthJwtUtil {
    private final JwtProperties jwtProperties;

    public Actor getActorFromClaims(Claims claims) {
        String actorId = (String) claims.get(JwtProperties.ACTOR_ID);
        String actorRole = (String) claims.get(JwtProperties.ACTOR_ROLE);
        return Actor.from(NixID.of(actorId), NixRole.valueOf(actorRole));
    }

    public Principal getPrincipalFromClaims(Claims claims) {
        String id = claims.getSubject();
        String role = (String) claims.get(JwtProperties.ROLE);
        return Principal.from(NixID.of(id), NixRole.valueOf(role));
//        if (businessId != null && !businessId.equals(id)) {
//            log.error("Invalid JWT token: This token {} cannot access this entity subdomain {}", id, businessId);
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
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty");
        }
        return null;
    }


    public static Optional<String> getJwtFromRequest(HttpServletRequest request) {
        if (request == null)
            return Optional.empty();
        if (request.getHeader("Authorization") != null)
            return Optional.ofNullable(getJwtFromHeader(request));
        if (request.getCookies() != null)
            return Optional.ofNullable(getJwtFromCookie(request));
        return Optional.empty();
    }

    private static String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
            return bearerToken.substring(7);
        return null;
    }

    private static String getJwtFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null)
            return null;
        Optional<Cookie> token = Arrays.stream(request.getCookies()).filter(cookie_ -> cookie_.getName().equals("token")).findFirst();
//        Optional<Cookie> refreshToken = Arrays.stream(request.getCookies()).filter(cookie_ -> cookie_.getName().equals("refreshToken")).findFirst();
        return token.map(Cookie::getValue).orElse(null);
    }

}
