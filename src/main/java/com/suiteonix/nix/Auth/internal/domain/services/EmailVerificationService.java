package com.suiteonix.nix.Auth.internal.domain.services;

import com.suiteonix.nix.Auth.internal.domain.AuthProfileModel;
import com.suiteonix.nix.Auth.internal.infrastructure.AuthUserMapper;
import com.suiteonix.nix.Auth.internal.infrastructure.MailJwtUtil;
import com.suiteonix.nix.Auth.service.AuthToken;
import com.suiteonix.nix.Auth.service.AuthTokenType;
import com.suiteonix.nix.Mail.NixMailSender;
import com.suiteonix.nix.Mail.TemplateType;
import com.suiteonix.nix.Common.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.Duration;

@Component
@RequiredArgsConstructor
class EmailVerificationService {

    private final MailJwtUtil mailJwtUtil;
    private final JwtProperties jwtProperties;
    @Value("${app.url}")
    private String appUrl;

    void issueAndSend(AuthProfileModel authUser) {
        long ttlMs = jwtProperties.getEmailVerificationTTL();
        Duration ttl = Duration.ofMillis(ttlMs);

        String otpCode = String.format("%06d", new SecureRandom().nextInt(1_000_000));
        String jwtToken = mailJwtUtil.generateEmailVerificationToken(authUser.getId(), ttlMs);

        authUser.getTokens().add(AuthToken.NEW(AuthTokenType.EMAIL_VERIFICATION_OTP, otpCode, ttl));
        authUser.getTokens().add(AuthToken.NEW(AuthTokenType.EMAIL_VERIFICATION_JWT, jwtToken, ttl));
        var orgIDParam =
                !authUser.getOrgID().isEmpty()
                        ? "&orgID=" + authUser.getOrgID().value()
                        : "";
        String verificationLink = "http://" + appUrl + "/auth/verify-email?token=" + jwtToken + orgIDParam;

        NixMailSender.newInstance()
                .to(authUser.getEmail().get())
                .templateName("auth/user-mail-verification")
                .variable("authUser", AuthUserMapper.INSTANCE.toDto(authUser))
                .variable("verificationToken", otpCode)
                .variable("verificationLink", verificationLink)
                .html()
                .templateType(TemplateType.THYMELEAF)
                .queueMail();
    }
}
