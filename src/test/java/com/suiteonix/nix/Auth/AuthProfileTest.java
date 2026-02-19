package com.suiteonix.nix.Auth;

import com.suiteonix.nix.shared.ValueObjects.Email;
import com.suiteonix.nix.shared.ValueObjects.Password;
import com.suiteonix.nix.shared.ValueObjects.Phone;
import com.suiteonix.nix.shared.ids.NixID;
import com.suiteonix.nix.shared.ids.NixRole;
import com.suiteonix.nix.Auth.service.AuthProfile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AuthProfileTest {

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

        AuthProfile adminUser = new AuthProfile(
                adminId,
                NixRole.ADMIN,
                email,
                phone,
                AuthProfile.SignInOptions.builder().build(),
                AuthProfile.ConfigFlags.builder().build(),
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

        AuthProfile authProfile = new AuthProfile(
                testId,
                NixRole.CUSTOMER,
                email,
                phone,
                AuthProfile.SignInOptions.builder().build(),
                AuthProfile.ConfigFlags.builder().build(),
                testId,
                null
        );

        assertNotNull(authProfile);
        assertThat(authProfile.id()).isEqualTo(testId);
        assertThat(authProfile.role()).isEqualTo(NixRole.CUSTOMER);
        assertThat(authProfile.email()).isEqualTo(email);
        assertThat(authProfile.phone()).isEqualTo(phone);
        assertThat(authProfile.orgID()).isEqualTo(testId);
    }
}