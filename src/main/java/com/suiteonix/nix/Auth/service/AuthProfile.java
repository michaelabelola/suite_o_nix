package com.suiteonix.db.nix.Auth.service;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.suiteonix.db.nix.shared.ValueObjects.Email;
import com.suiteonix.db.nix.shared.ValueObjects.Phone;
import com.suiteonix.db.nix.shared.audit.AuditSection;
import com.suiteonix.db.nix.shared.ids.NixID;
import com.suiteonix.db.nix.shared.ids.NixRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(name = "AuthProfile")
public record AuthProfile(
        @JsonUnwrapped
        NixID id,
        @JsonUnwrapped
        NixRole role,
        @JsonUnwrapped(prefix = "email_")
        Email email,
        @JsonUnwrapped(prefix = "phone_")
        Phone phone,
        @JsonUnwrapped(prefix = "owner_")
        NixID ownerId,
        AuditSection audit
) {
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
            ConfigFlags configFlags
    ) {

        /**
         * Defines various configuration flags to control the behavior of the user registration process.
         * These settings allow for fine-grained control over authentication methods, password policies,
         * and verification procedures.
         *
         * @param emailAndPassword       Enable authentication using email and password.
         * @param emailAndEmailToken     Enable authentication using a one-time token sent to the user's email.
         * @param phoneAndPassword       Enable authentication using a phone number and password.
         * @param phoneAndPhoneToken     Enable authentication using a one-time token sent to the user's phone.
         * @param jwtAuthEnabled         Enable JSON Web Token (JWT) based authentication for the session.
         * @param linkedAccountLogin     Allow users to sign in using linked third-party accounts (e.g., Google, Facebook).
         * @param generateRandomPassword Automatically generate a secure, random password for the new user.
         * @param forwardPasswordToMail  If a random password is generated, send it to the user's email.
         * @param requirePasswordChange  Force the user to change their password upon their first login.
         * @param sendMailVerification   Send a verification link to the user's email to confirm their address.
         * @param enableOwnerLogin       Allow the primary owner of the account/tenant to log in.
         * @param sendPhoneVerification  Send a verification code (OTP) to the user's phone to confirm their number.
         */
        @Schema(name = "AuthProfile.Register.ConfigFlags",
                $comment = "Defines various configuration flags to control the behavior of the user registration process. These settings allow for fine-grained control over authentication methods, password policies, and verification procedures.")
        public record ConfigFlags(
                /** Enables authentication with email and password. */
                boolean emailAndPassword,
                /** Enables authentication with email and a one-time token. */
                boolean emailAndEmailToken,
                /** Enables authentication with phone number and password. */
                boolean phoneAndPassword,
                /** Enables authentication with phone number and a one-time token. */
                boolean phoneAndPhoneToken,
                /** Enables JWT authentication. */
                boolean jwtAuthEnabled,
                /** Allows login via linked social or external accounts. */
                boolean linkedAccountLogin,
                /** Automatically generates a random password for new users. */
                boolean generateRandomPassword,
                /** Forwards the generated password to the user's email. */
                boolean forwardPasswordToMail,
                /** Requires the user to change their password on first login. */
                boolean requirePasswordChange,
                /** Sends an email verification link upon registration. */
                boolean sendMailVerification,
                /** Allows the account owner to log in. */
                boolean enableOwnerLogin,
                /** Sends a phone number verification code upon registration. */
                boolean sendPhoneVerification
        ) {
        }
    }
}
