package com.suiteonix.nix.kernel.security;

import com.suiteonix.nix.Common.authentication.CustomAuthentication;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final AuthJwtUtil authJwtUtil;
    @Value("${app.url:localhost:8080}")
    private String appBaseUrl;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        Optional<String> jwt = AuthJwtUtil.getJwtFromRequest(request);
        if (jwt.isPresent()) {
            var claims = authJwtUtil.getClaimsFromToken(jwt.get());
            var auth = CustomAuthentication.of(
                    authJwtUtil.getPrincipalFromClaims(claims),
                    authJwtUtil.getActorFromClaims(claims),
                    new HashSet<>()
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(CustomAuthentication.ofSystem());
        filterChain.doFilter(request, response);
    }
    public String getBusinessIdFromSubDomain(HttpServletRequest request) {
        String serverName = request.getServerName().toLowerCase();
        String[] parts = serverName.split("%s".formatted(appBaseUrl.split(":")[0]));
//        String[] parts = serverName.split("\\.");
//        // If we have a subdomain (e.g., business123.domain.com)
//        if (parts.length > 2) {
//            return parts[0];
//        }
        if (parts.length > 0 && !parts[0].isBlank())
            return parts[0].split("\\.")[0];
//            return parts[0].split("\\.")[parts[0].split("\\.").length - 1];

        return null;
    }
}
