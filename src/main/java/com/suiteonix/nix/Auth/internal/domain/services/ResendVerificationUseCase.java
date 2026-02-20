package com.suiteonix.nix.Auth.internal.domain.services;

import com.suiteonix.nix.Auth.internal.domain.AuthProfileModel;
import com.suiteonix.nix.Auth.service.AuthToken;
import com.suiteonix.nix.Auth.service.AuthTokenType;
import com.suiteonix.nix.Auth.internal.infrastructure.AuthUserRepository;
import com.suiteonix.nix.Mail.MailService;
import com.suiteonix.nix.kernel.security.AuthJwtUtil;
import com.suiteonix.nix.kernel.security.jwt.JwtProperties;
import com.suiteonix.nix.Mail.NixMailSender;
import com.suiteonix.nix.Mail.TemplateType;
import com.suiteonix.nix.shared.ddd.UseCase;
import com.suiteonix.nix.shared.exceptions.EX;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Duration;

@UseCase
@RequiredArgsConstructor
class ResendVerificationUseCase {

    private final AuthUserRepository repository;
    private final AuthUserLookupHelper lookupHelper;
    private final AuthJwtUtil authJwtUtil;
    private final JwtProperties jwtProperties;
    private final MailService mailService;
    @Value("${app.url}")
    private String appUrl;

    @Transactional
    public void resend(String email, String ownerId) {
        AuthProfileModel authUser = lookupHelper.findByEmailScoped(email, ownerId);

        if (authUser.isEmailVerified())
            throw EX.conflict("EMAIL_ALREADY_VERIFIED", "This email address has already been verified");

        // Remove old verification tokens
        authUser.getTokens().removeIf(t ->
                t.type() == AuthTokenType.EMAIL_VERIFICATION_OTP
                        || t.type() == AuthTokenType.EMAIL_VERIFICATION_JWT
                        || t.type() == AuthTokenType.EMAIL_VERIFICATION);

        long ttlMs = jwtProperties.getEmailVerificationTTL();
        Duration ttl = Duration.ofMillis(ttlMs);

        String otpCode = String.format("%06d", new SecureRandom().nextInt(1_000_000));
        String jwtToken = authJwtUtil.generateEmailVerificationToken(authUser.getId(), ttlMs);

        authUser.getTokens().add(AuthToken.NEW(AuthTokenType.EMAIL_VERIFICATION_OTP, otpCode, ttl));
        authUser.getTokens().add(AuthToken.NEW(AuthTokenType.EMAIL_VERIFICATION_JWT, jwtToken, ttl));

        String verificationLink = "http://" + appUrl + "/auth/verify-email?token=" + jwtToken
                + "&orgID=" + authUser.getOrgID().get();

        repository.save(authUser);

        mailService.queueMail(

                NixMailSender.newInstance()
                        .to(authUser.getEmail().get())
                        .html()
                        .templateName("auth/user-mail-verification")
                        .variable("authUser", authUser)
                        .variable("verificationToken", otpCode)
                        .variable("verificationLink", verificationLink)
                        .templateType(TemplateType.THYMELEAF)
        );
    }
}
