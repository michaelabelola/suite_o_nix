package com.suiteonix.nix.Auth.internal;

import com.suiteonix.nix.Auth.service.AuthProfile;
import com.suiteonix.nix.shared.exceptions.EX;
import com.suiteonix.nix.shared.ids.NixID;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class AuthModule {
    private final AuthUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final RegisterUseCase registerUseCase;

    @Transactional
    public AuthUserModel register(AuthProfile.Register create) {
        return registerUseCase.execute(create);
    }

    @Transactional
    AuthUserModel update(AuthUserModel authUser) {
        return repository.save(authUser);
    }

    @Transactional
    void delete(AuthUserModel authUser) {
        repository.delete(authUser);
    }

    @Cacheable(value = "authUser", key = "#id")
    @Transactional(readOnly = true)
    public AuthUserModel getAuthUserById(String id) {
        return repository
                .findById(NixID.of(id))
                .orElseThrow(
                        () ->
                                EX.notFound(
                                        "AUTH_USER_NOT_FOUND",
                                        "Auth user not found"));
    }
}
