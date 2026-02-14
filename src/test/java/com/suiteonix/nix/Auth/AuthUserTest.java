package com.suiteonix.nix.Auth;

import com.suiteonix.nix.shared.ValueObjects.Email;
import com.suiteonix.nix.shared.ValueObjects.Password;
import com.suiteonix.nix.shared.ValueObjects.Phone;
import com.suiteonix.nix.shared.ids.NixID;
import com.suiteonix.nix.shared.ids.NixRole;
import com.suiteonix.nix.spi.Auth.AuthUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AuthUserTest {

    @Test
    void id() {
    }

    @Test
    void role() {
    }

    @Test
    void email() {
    }

    @Test
    void phone() {
    }

    @Test
    void password() {
    }

    @Test
    void ownerId() {
    }

    @Test
    void audit() {
    }

    @Test
    @DisplayName("AuthUser record should support different roles")
    void authUser_ShouldSupportDifferentRoles() {
        NixID adminId = NixID.of(UUID.randomUUID().toString());
        Email email = Email.NEW("admin@example.com");
        Phone phone = Phone.NEW("+1234567890");
        Password password = new Password("adminPassword");

        AuthUser adminUser = new AuthUser(
                adminId,
                NixRole.ADMIN,
                email,
                phone,
                password,
                adminId,
                null
        );

        assertThat(adminUser.role()).isEqualTo(NixRole.ADMIN);
    }

    @Test
    @DisplayName("AuthUser record should contain all required fields")
    void authUser_ShouldContainAllRequiredFields() {
        NixID testId = NixID.of(UUID.randomUUID().toString());
        Email email = Email.NEW("user@example.com");
        Phone phone = Phone.NEW("+1234567890");
        Password password = new Password("encodedPassword");

        AuthUser authUser = new AuthUser(
                testId,
                NixRole.CUSTOMER,
                email,
                phone,
                password,
                testId,
                null
        );

        assertNotNull(authUser);
        assertThat(authUser.id()).isEqualTo(testId);
        assertThat(authUser.role()).isEqualTo(NixRole.CUSTOMER);
        assertThat(authUser.email()).isEqualTo(email);
        assertThat(authUser.phone()).isEqualTo(phone);
        assertThat(authUser.password()).isEqualTo(password);
        assertThat(authUser.ownerId()).isEqualTo(testId);
    }
}