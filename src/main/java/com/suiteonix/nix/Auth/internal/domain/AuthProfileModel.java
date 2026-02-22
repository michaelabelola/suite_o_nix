package com.suiteonix.nix.Auth.internal.domain;

import com.suiteonix.nix.Auth.service.AuthProfile;
import com.suiteonix.nix.Auth.service.AuthToken;
import com.suiteonix.nix.Common.audit.IAuditableOwnableEntity;
import com.suiteonix.nix.Common.ddd.AggregateRoot;
import com.suiteonix.nix.shared.ConfigFlag;
import com.suiteonix.nix.shared.ValueObjects.Email;
import com.suiteonix.nix.shared.ValueObjects.Password;
import com.suiteonix.nix.shared.ValueObjects.Phone;
import com.suiteonix.nix.shared.ids.NixID;
import com.suiteonix.nix.shared.ids.NixIDImpl;
import com.suiteonix.nix.shared.ids.NixRole;
import com.suiteonix.nix.shared.interfaces.EmptyChecker;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "auth_profile")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@AggregateRoot
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder()
public class AuthProfileModel extends IAuditableOwnableEntity<AuthProfileModel> {

    @EmbeddedId
    NixIDImpl id;

    @Enumerated(EnumType.STRING)
    NixRole role;

    @Embedded
    Email email;

    @Embedded
    Phone phone;

    @Embedded
    Password password;

    @Builder.Default
    @Type(JsonType.class)
    @Column(name = "sign_in_options", columnDefinition = "jsonb")
    SignInOptions signInOptions = new SignInOptions();

    @Builder.Default
    @Type(JsonType.class)
    @Column(name = "config_flags", columnDefinition = "jsonb")
    ConfigFlags configFlags = new ConfigFlags();

    @Builder.Default
    @Type(JsonType.class)
    @Column(name = "tokens", columnDefinition = "jsonb")
    Set<AuthToken> tokens = new HashSet<>();

    @Builder.Default
    @Column(name = "email_verified")
    boolean emailVerified = false;

    public void enableProxySignIn(NixID id) {
        if (signInOptions == null) signInOptions = SignInOptions.EMPTY();
        signInOptions.setProxySignIn(ConfigFlag.ACTIVE);
        signInOptions.proxyUserID = id.to(NixID::NEW);
    }

    public static AuthProfileModel NEW(AuthProfile.Register register, Set<AuthToken> authTokens, PasswordEncoder encoder) {
        return new AuthProfileModel(
                NixID.NEW(register.role()),
                register.role(),
                Email.NEW(register.email()),
                Phone.NEW(register.phone()),
                Password.NewEncodedPassword(register.password(), encoder),
                SignInOptions.NEW(register.signInOptions()),
                ConfigFlags.NEW(register.configFlags()),
                authTokens == null ? new HashSet<>() : authTokens,
                false
        );
    }


    public static AuthProfileModel NEW(NixRole role, SignInOptions signInOptions, ConfigFlags configFlags, NixID orgID) {
        var profile = new AuthProfileModel();
        profile.id = NixID.NEW(role);
        profile.role = role;
        profile.signInOptions = signInOptions;
        profile.configFlags = configFlags;
        profile.setOrgID(orgID.to(NixID::NEW));
        return profile;
    }

    public static AuthProfileModel NEW(
            NixID id,
            NixRole role,
            Email email,
            Phone phone,
            Password password,
            AuthProfile.SignInOptions signInOptions,
            AuthProfile.ConfigFlags newFlags
    ) {
        return new AuthProfileModel(
                id.to(NixID::NEW),
                role,
                email,
                phone,
                password,
                SignInOptions.NEW(signInOptions),
                ConfigFlags.NEW(newFlags),
                new HashSet<>(),
                false
        );
    }

    @Data
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class SignInOptions {
        private ConfigFlag emailAndPassword;
        /**
         * Enables authentication with email and a one-time token.
         */
        private ConfigFlag emailAndEmailToken;
        /**
         * Enables authentication with phone number and password.
         */
        private ConfigFlag phoneAndPassword;
        /**
         * Enables authentication with phone number and a one-time token.
         */
        private ConfigFlag phoneAndPhoneToken;

        private ConfigFlag proxySignIn;

        private NixIDImpl proxyUserID;

        public static SignInOptions NEW(AuthProfile.SignInOptions options) {
            if (options == null) return null;
            SignInOptions flags = new SignInOptions();
            flags.emailAndPassword = options.emailAndPassword();
            flags.emailAndEmailToken = options.emailAndEmailToken();
            flags.phoneAndPassword = options.phoneAndPassword();
            flags.phoneAndPhoneToken = options.phoneAndPhoneToken();
            return flags;
        }

        public static SignInOptions EMPTY() {
            return new SignInOptions();
        }

        public static SignInOptions NEW_INACTIVE() {
            SignInOptions flags = new SignInOptions();
            flags.emailAndPassword = ConfigFlag.INACTIVE;
            flags.emailAndEmailToken = ConfigFlag.INACTIVE;
            flags.phoneAndPassword = ConfigFlag.INACTIVE;
            flags.phoneAndPhoneToken = ConfigFlag.INACTIVE;
            return flags;
        }

        public static SignInOptions NEW_DISABLED() {
            SignInOptions flags = new SignInOptions();
            flags.emailAndPassword = ConfigFlag.DISABLED;
            flags.emailAndEmailToken = ConfigFlag.DISABLED;
            flags.phoneAndPassword = ConfigFlag.DISABLED;
            flags.phoneAndPhoneToken = ConfigFlag.DISABLED;
            return flags;
        }
    }

    @Data
    public static class ConfigFlags implements EmptyChecker {
        /**
         * Enables JWT authentication.
         */
        private ConfigFlag jwtAuthEnabled;
        /**
         * Allows login via linked social or external accounts.
         */
        private ConfigFlag linkedAccountLogin;
        /**
         * Automatically generates a random password for new users.
         */
        private ConfigFlag generateRandomPassword;
        /**
         * Forwards the generated password to the user's email.
         */
        private ConfigFlag forwardPasswordToMail;
        /**
         * Requires the user to change their password on first login.
         */
        private ConfigFlag requirePasswordChange;
        /**
         * Sends an email verification link upon registration.
         */
        private ConfigFlag sendMailVerification;
        /**
         * Allows the account owner to log in.
         */
        private ConfigFlag enableOwnerLogin;

        public static ConfigFlags NEW(AuthProfile.ConfigFlags create) {
            ConfigFlags flags = new ConfigFlags();
            if (create == null) return flags;
            flags.jwtAuthEnabled = create.jwtAuthEnabled();
            flags.linkedAccountLogin = create.linkedAccountLogin();
            flags.generateRandomPassword = create.generateRandomPassword();
            flags.forwardPasswordToMail = create.forwardPasswordToMail();
            flags.requirePasswordChange = create.requirePasswordChange();
            flags.sendMailVerification = create.sendMailVerification();
            flags.enableOwnerLogin = create.enableOwnerLogin();
//            flags.sendPhoneVerification = create.sendPhoneVerification();
            return flags;
        }

        public static ConfigFlags EMPTY() {
            return new ConfigFlags();
        }

        @Override
        public boolean isEmpty() {
            return jwtAuthEnabled == null ||
                    linkedAccountLogin == null ||
                    generateRandomPassword == null ||
                    forwardPasswordToMail == null ||
                    requirePasswordChange == null ||
                    sendMailVerification == null ||
                    enableOwnerLogin == null;
        }
    }
}
