package com.suiteonix.nix.Auth.internal.domain;

import com.suiteonix.nix.Auth.internal.infrastructure.AuthUserMapper;
import com.suiteonix.nix.Auth.service.AuthProfile;
import com.suiteonix.nix.Auth.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthModule authModule;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public @NonNull AuthProfile register(AuthProfile.@NonNull Register registerDto) {
        var user = authModule.register(registerDto);

        return AuthUserMapper.INSTANCE.toDto(user);
    }
}
