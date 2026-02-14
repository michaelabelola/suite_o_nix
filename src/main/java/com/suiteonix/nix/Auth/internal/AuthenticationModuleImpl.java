package com.suiteonix.nix.Auth.internal;

import com.suiteonix.nix.Auth.AuthUser;
import com.suiteonix.nix.Auth.AuthUserRegisterDto;
import com.suiteonix.nix.Auth.AuthenticationModule;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationModuleImpl implements AuthenticationModule {
    private final AuthUserService authUserService;

    @Override
    public @NonNull AuthUser register(@NonNull AuthUserRegisterDto registerDto) {
        var user = authUserService.register(registerDto);
        return AuthUserMapper.INSTANCE.toDto(user);
    }
}
