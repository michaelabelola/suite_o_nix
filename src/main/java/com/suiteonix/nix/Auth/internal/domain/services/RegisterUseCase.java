package com.suiteonix.nix.Auth.internal.domain.services;

import com.suiteonix.nix.Auth.internal.domain.AuthProfileModel;
import com.suiteonix.nix.Auth.internal.infrastructure.AuthUserMapper;
import com.suiteonix.nix.Auth.internal.infrastructure.AuthUserRepository;
import com.suiteonix.nix.Auth.service.AuthProfile;
import com.suiteonix.nix.Auth.service.AuthToken;
import com.suiteonix.nix.Auth.service.AuthTokenType;
import com.suiteonix.nix.Auth.service.ConfigFlag;
import com.suiteonix.nix.Mail.NixMailSender;
import com.suiteonix.nix.Mail.TemplateType;
import com.suiteonix.nix.kernel.security.AuthJwtUtil;
import com.suiteonix.nix.kernel.security.jwt.JwtProperties;
import com.suiteonix.nix.shared.ValueObjects.Email;
import com.suiteonix.nix.shared.ValueObjects.Password;
import com.suiteonix.nix.shared.ValueObjects.Phone;
import com.suiteonix.nix.shared.ddd.UseCase;
import com.suiteonix.nix.shared.exceptions.EX;
import com.suiteonix.nix.shared.principal.Principal;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Duration;

@UseCase
@RequiredArgsConstructor
class RegisterUseCase {

    private final AuthUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthJwtUtil authJwtUtil;
    private final JwtProperties jwtProperties;
    @Value("${app.url}")
    private String appUrl;

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
            generateEmailVerificationToken(authUser);
        return repository.save(authUser);
    }

    private void generateEmailVerificationToken(AuthProfileModel authUser) {
        long ttlMs = jwtProperties.getEmailVerificationTTL();

        String otpCode = String.format("%06d", new SecureRandom().nextInt(1_000_000));
        String jwtToken = authJwtUtil.generateEmailVerificationToken(authUser.getId(), ttlMs);

        Duration ttl = Duration.ofMillis(ttlMs);
        authUser.getTokens().add(AuthToken.NEW(AuthTokenType.EMAIL_VERIFICATION_OTP, otpCode, ttl));
        authUser.getTokens().add(AuthToken.NEW(AuthTokenType.EMAIL_VERIFICATION_JWT, jwtToken, ttl));

        String verificationLink = "http://" + appUrl + "/auth/verify-email?token=" + jwtToken
                + "&orgID=" + authUser.getOrgID().get();
        NixMailSender.newInstance()
                .to(authUser.getEmail().get())
                .templateName("auth/user-mail-verification")
                .variable("authUser", AuthUserMapper.INSTANCE.toDto(authUser))
                .variable("verificationToken", otpCode)
                .html()
                .variable("verificationLink", verificationLink)
                .templateType(TemplateType.THYMELEAF).queueMail();
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
