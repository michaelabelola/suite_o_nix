package com.suiteonix.nix.Auth.service;

import org.jspecify.annotations.NonNull;

public interface AuthenticationService {
    @NonNull AuthProfile register(AuthProfile.@NonNull Register registerDto);
}
