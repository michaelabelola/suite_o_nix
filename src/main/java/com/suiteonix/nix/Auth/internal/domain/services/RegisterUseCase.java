package com.suiteonix.nix.Auth.internal.domain.services;

import com.suiteonix.nix.Auth.internal.domain.AuthProfileModel;
import com.suiteonix.nix.Auth.internal.infrastructure.AuthUserRepository;
import com.suiteonix.nix.Auth.service.AuthProfile;
import com.suiteonix.nix.shared.ConfigFlag;
import com.suiteonix.nix.shared.ValueObjects.Email;
import com.suiteonix.nix.shared.ValueObjects.Password;
import com.suiteonix.nix.shared.ValueObjects.Phone;
import com.suiteonix.nix.Common.ddd.UseCase;
import com.suiteonix.nix.shared.exceptions.EX;
import com.suiteonix.nix.shared.principal.Principal;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
class RegisterUseCase {

    private final AuthUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final EmailVerificationService emailVerificationService;

    @Transactional
    public AuthProfileModel execute(AuthProfile.Register create) {
        AuthProfileModel authUser = AuthProfileModel.NEW(
                create.id(),
                create.role(),
                Email.NEW(create.email()),
                Phone.NEW(create.phone()),
                Password.NewEncodedPassword(create.password(), passwordEncoder),
                create.signInOptions(),
                create.configFlags()
        );
        if (!authUser.getEmail().isEmpty()) verifyDuplicateEmail(authUser.getEmail());
        if (!authUser.getPhone().isEmpty()) verifyDuplicatePhone(authUser.getPhone());
        if (create.configFlags() != null && create.configFlags().sendMailVerification() == ConfigFlag.ACTIVE)
            emailVerificationService.issueAndSend(authUser);
        return repository.save(authUser);
    }

    private void verifyDuplicatePhone(@Nullable Phone string) {
        repository.findByPhoneAndOrgID(string, Principal.ID())
                .ifPresent(authUserModel -> {
                    throw EX.conflict("PHONE_ALREADY_REGISTERED", "The provided phone number is already registered for another account authentication");
                });
    }

    private void verifyDuplicateEmail(@Nullable Email s) {
        repository.findByEmailAndOrgID(s, Principal.ID())
                .ifPresent(authUserModel -> {
                    throw EX.conflict("EMAIL_ALREADY_REGISTERED", "The provided email is already registered for another account authentication");
                });
    }

    private void processConfigFlags(AuthProfile.Register create) {
        var flags = create.configFlags();
    }

}
