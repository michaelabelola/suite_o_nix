package com.suiteonix.nix.Auth.internal.domain.services;

import com.suiteonix.nix.Auth.internal.domain.AuthProfileModel;
import com.suiteonix.nix.Auth.internal.infrastructure.AuthUserRepository;
import com.suiteonix.nix.Auth.service.AuthProfile;
import com.suiteonix.nix.shared.exceptions.EX;
import com.suiteonix.nix.shared.ids.NixID;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthModule {
    private final AuthUserRepository repository;
    private final RegisterUseCase registerUseCase;
    private final VerifyEmailUseCase verifyEmailUseCase;
    private final ResendVerificationUseCase resendVerificationUseCase;

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
    public AuthProfileModel getAuthUserById(Long id) {
        return repository
                .findById(NixID.of(id))
                .orElseThrow(
                        () ->
                                EX.notFound(
                                        "AUTH_USER_NOT_FOUND",
                                        "Auth user not found"));
    }

    @Transactional
    public void verifyEmailByJwt(String token, String orgID) {
        verifyEmailUseCase.verifyByJwt(token, orgID);
    }

    @Transactional
    public void verifyEmailByOtp(String email, String otp, Long orgID) {
        verifyEmailUseCase.verifyByOtp(email, otp, orgID);
    }

    @Transactional
    public void resendVerification(String email, Long orgID) {
        resendVerificationUseCase.resend(email, orgID);
    }
}
