package com.suiteonix.nix.Auth.internal;

import com.suiteonix.nix.Auth.service.AuthProfile;
import com.suiteonix.nix.shared.exceptions.EX;
import com.suiteonix.nix.shared.ids.NixID;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class AuthModule {
    private final AuthUserRepository repository;
    private final RegisterUseCase registerUseCase;

    @Transactional
    public AuthProfileModel register(AuthProfile.Register create) {
        return registerUseCase.execute(create);
    }

    @Transactional
    AuthProfileModel update(AuthProfileModel authUser) {
        return repository.save(authUser);
    }

    @Transactional
    void delete(AuthProfileModel authUser) {
        repository.delete(authUser);
    }

    @Cacheable(value = "authUser", key = "#id")
    @Transactional(readOnly = true)
    public AuthProfileModel getAuthUserById(String id) {
        return repository
                .findById(NixID.of(id))
                .orElseThrow(
                        () ->
                                EX.notFound(
                                        "AUTH_USER_NOT_FOUND",
                                        "Auth user not found"));
    }
}
