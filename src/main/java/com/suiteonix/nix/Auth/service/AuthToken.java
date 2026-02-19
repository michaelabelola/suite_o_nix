package com.suiteonix.nix.Auth.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Transient;

import java.time.Duration;
import java.time.Instant;

public record AuthToken(
        AuthTokenType type,
        String value,
        Instant createdAt,
        Duration duration
) {
    public AuthToken {
        if (createdAt == null)
            createdAt = Instant.now();
    }

    @Transient
    @JsonIgnore
    public boolean isNotExpired() {
        if (duration == null || createdAt == null) return true;
        return !Instant.now().isAfter(createdAt.plusMillis(duration.toMillis()));
    }

    public static AuthToken EMPTY() {
        return new AuthToken(null, null, null, null);
    }

    public static AuthToken NEW(AuthTokenType authTokenType, String s) {
        return new AuthToken(authTokenType, s, null, null);
    }

    public static AuthToken NEW(AuthTokenType authTokenType, String value, Duration duration) {
        return new AuthToken(authTokenType, value, Instant.now(), duration);
    }
}
