package com.suiteonix.db.nix.Auth.internal;

import com.suiteonix.db.nix.shared.ValueObjects.Email;
import com.suiteonix.db.nix.shared.ValueObjects.Password;
import com.suiteonix.db.nix.shared.ValueObjects.Phone;
import com.suiteonix.db.nix.shared.audit.IAuditableOwnableEntity;
import com.suiteonix.db.nix.shared.ddd.AggregateRoot;
import com.suiteonix.db.nix.shared.ids.NixID;
import com.suiteonix.db.nix.shared.ids.NixRole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "auth_user")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@AggregateRoot
public class AuthUserModel extends IAuditableOwnableEntity<AuthUserModel> {

    @EmbeddedId
    NixID id;

    @Enumerated(EnumType.STRING)
    NixRole role;

    @Embedded
    Email email;

    @Embedded
    Phone phone;

    @Embedded
    Password password;

//    ConfigFlags

    public static AuthUserModel NEW(
            NixID nixID,
            NixRole role,
            Email email,
            Phone phone,
            Password password
    ) {
        return new AuthUserModel(
                nixID,
                role,
                email,
                phone,
                password
        );
    }


    @Embeddable
    @Data
    public static class OwnerConfigFlags {
        boolean emailAndPassword;
        /**
         * Enables authentication with email and a one-time token.
         */
        boolean emailAndEmailToken;
        /**
         * Enables authentication with phone number and password.
         */
        boolean phoneAndPassword;
        /**
         * Enables authentication with phone number and a one-time token.
         */
        boolean phoneAndPhoneToken;
        /**
         * Enables JWT authentication.
         */
        boolean jwtAuthEnabled;
        /**
         * Allows login via linked social or external accounts.
         */
        boolean linkedAccountLogin;
        /**
         * Automatically generates a random password for new users.
         */
        boolean generateRandomPassword;
        /**
         * Forwards the generated password to the user's email.
         */
        boolean forwardPasswordToMail;
        /**
         * Requires the user to change their password on first login.
         */
        boolean requirePasswordChange;
        /**
         * Sends an email verification link upon registration.
         */
        boolean sendMailVerification;
        /**
         * Allows the account owner to log in.
         */
        boolean enableOwnerLogin;
        /**
         * Sends a phone number verification code upon registration.
         */
        boolean sendPhoneVerification;
    }

//    public static class ConfigFlags

    @Embeddable
    @Data
    public static class ConfigFlags {
        boolean emailAndPassword;
        /**
         * Enables authentication with email and a one-time token.
         */
        boolean emailAndEmailToken;
        /**
         * Enables authentication with phone number and password.
         */
        boolean phoneAndPassword;
        /**
         * Enables authentication with phone number and a one-time token.
         */
        boolean phoneAndPhoneToken;
        /**
         * Enables JWT authentication.
         */
        boolean jwtAuthEnabled;
        /**
         * Allows login via linked social or external accounts.
         */
        boolean linkedAccountLogin;
        /**
         * Automatically generates a random password for new users.
         */
        boolean generateRandomPassword;
        /**
         * Forwards the generated password to the user's email.
         */
        boolean forwardPasswordToMail;
        /**
         * Requires the user to change their password on first login.
         */
        boolean requirePasswordChange;
        /**
         * Sends an email verification link upon registration.
         */
        boolean sendMailVerification;
        /**
         * Allows the account owner to log in.
         */
        boolean enableOwnerLogin;
        /**
         * Sends a phone number verification code upon registration.
         */
        boolean sendPhoneVerification;
    }
}
