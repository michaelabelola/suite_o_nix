package com.suiteonix.nix.spi.Auth;

import org.jspecify.annotations.NonNull;

public interface AuthenticationModule {
    @NonNull AuthUser register(@NonNull AuthUserRegisterDto registerDto);
}
