package com.suiteonix.nix.Auth;

import org.jspecify.annotations.NonNull;

public interface AuthenticationModule {
    @NonNull AuthUser register(@NonNull AuthUserRegisterDto registerDto);
}
