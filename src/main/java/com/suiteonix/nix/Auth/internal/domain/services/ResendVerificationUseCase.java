package com.suiteonix.nix.Auth.internal.domain.services;

import com.suiteonix.nix.Auth.internal.domain.AuthProfileModel;
import com.suiteonix.nix.Auth.service.AuthTokenType;
import com.suiteonix.nix.Auth.internal.infrastructure.AuthUserRepository;
import com.suiteonix.nix.Common.ddd.UseCase;
import com.suiteonix.nix.shared.exceptions.EX;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
class ResendVerificationUseCase {

    private final AuthUserRepository repository;
    private final AuthUserLookupHelper lookupHelper;
    private final EmailVerificationService emailVerificationService;

    @Transactional
    public void resend(String email, Long ownerId) {
        AuthProfileModel authUser = lookupHelper.findByEmailScoped(email, ownerId);

        if (authUser.isEmailVerified())
            throw EX.conflict("EMAIL_ALREADY_VERIFIED", "This email address has already been verified");

        // Remove old verification tokens
        authUser.getTokens().removeIf(t ->
                t.type() == AuthTokenType.EMAIL_VERIFICATION_OTP
                        || t.type() == AuthTokenType.EMAIL_VERIFICATION_JWT);

        emailVerificationService.issueAndSend(authUser);
        repository.save(authUser);
    }
}
