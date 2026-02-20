package com.suiteonix.nix.Auth.service;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.suiteonix.nix.shared.audit.AuditSection;
import com.suiteonix.nix.shared.ids.NixID;
import com.suiteonix.nix.shared.ids.NixRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(name = "AuthProfile")
public record AuthProfile(
        @JsonUnwrapped
        NixID id,
        NixRole role,
        String email,
        String phone,
        @JsonUnwrapped(prefix = "org_")
        NixID orgID,
        SignInOptions signInOptions,
        ConfigFlags configFlags
) {
    @Schema(name = "AuthProfile.Detailed")
    public record Detailed(
            @JsonUnwrapped
            NixID id,
            NixRole role,
            String email,
            String phone,
            @JsonUnwrapped(prefix = "org_")
            NixID orgID,
            SignInOptions signInOptions,
            ConfigFlags configFlags,
            @JsonUnwrapped(prefix = "owner_")
            AuditSection audit
    ) {

    }

    @Builder
    @Schema(name = "AuthProfile.Register")
    public record Register(
            NixID id,
            @Schema(example = "CUSTOMER")
            NixRole role,
            @Schema(example = "michaelabelola@gmail.com")
            String email,
            @Schema(example = "+1234567890")
            String phone,
            @Schema(example = "SecurePass123!")
            String password,
            SignInOptions signInOptions,
            ConfigFlags configFlags
    ) {
    }

    /**
     * Defines various configuration flags to control the behavior of the user registration process.
     * These settings allow for fine-grained control over authentication methods, password policies,
     * and verification procedures.
     *
     * @param jwtAuthEnabled         Enable JSON Web Token (JWT) based authentication for the session.
     * @param linkedAccountLogin     Allow users to sign in using linked third-party accounts (e.g., Google, Facebook).
     * @param generateRandomPassword Automatically generate a secure, random password for the new user.
     * @param forwardPasswordToMail  If a random password is generated, send it to the user's email.
     * @param requirePasswordChange  Force the user to change their password upon their first login.
     * @param sendMailVerification   Send a verification link to the user's email to confirm their address.
     * @param enableOwnerLogin       Allow the primary owner of the account/tenant to log in.
     *                               // * @param sendPhoneVerification  Send a verification code (OTP) to the user's phone to confirm their number.
     */
    @Schema(name = "AuthProfile.Register.ConfigFlags",
            $comment = "Defines various configuration flags to control the behavior of the user registration process. These settings allow for fine-grained control over authentication methods, password policies, and verification procedures.")
    @Builder
    public record ConfigFlags(
            ConfigFlag jwtAuthEnabled,
            ConfigFlag linkedAccountLogin,
            ConfigFlag generateRandomPassword,
            ConfigFlag forwardPasswordToMail,
            ConfigFlag requirePasswordChange,
            ConfigFlag sendMailVerification,
            ConfigFlag enableOwnerLogin
    ) {
    }

    /**
     * @param emailAndPassword   Enable authentication using email and password.
     * @param emailAndEmailToken Enable authentication using a one-time token sent to the user's email.
     * @param phoneAndPassword   Enable authentication using a phone number and password.
     * @param phoneAndPhoneToken Enable authentication using a one-time token sent to the user's phone.
     */
    @Schema(name = "AuthProfile.Register.ConfigFlags",
            $comment = "Defines various configuration flags to control the behavior of the user registration process. These settings allow for fine-grained control over authentication methods, password policies, and verification procedures.")
    @Builder
    public record SignInOptions(
            ConfigFlag emailAndPassword,
            ConfigFlag emailAndEmailToken,
            ConfigFlag phoneAndPassword,
            ConfigFlag phoneAndPhoneToken
    ) {
    }
}
