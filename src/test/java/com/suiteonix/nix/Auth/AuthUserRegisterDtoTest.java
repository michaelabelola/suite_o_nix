package com.suiteonix.nix.Auth;

import com.suiteonix.nix.shared.ids.NixID;
import com.suiteonix.nix.shared.ids.NixRole;
import com.suiteonix.nix.spi.Auth.AuthUserRegisterDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AuthUserRegisterDtoTest {


    @Test
    @DisplayName("AuthUserRegisterDto should be created with valid data")
    void authUserRegisterDto_ShouldBeCreated_WithValidData() {

        var userId = NixID.NEW();
     var   validRegisterDto = new AuthUserRegisterDto(
                userId,
                NixRole.CUSTOMER,
                "test@example.com",
                "+1234567890",
                "SecurePass123!"
        );
        assertNotNull(validRegisterDto);
        assertThat(validRegisterDto.id()).isEqualTo(userId);
        assertThat(validRegisterDto.role()).isEqualTo(NixRole.CUSTOMER);
        assertThat(validRegisterDto.email()).isEqualTo("test@example.com");
        assertThat(validRegisterDto.phone()).isEqualTo("+1234567890");
        assertThat(validRegisterDto.password()).isEqualTo("SecurePass123!");
    }

    @Test
    @DisplayName("AuthUserRegisterDto should handle different user roles")
    void authUserRegisterDto_ShouldHandleDifferentRoles() {
        NixID adminId = NixID.of(UUID.randomUUID().toString());
        AuthUserRegisterDto adminDto = new AuthUserRegisterDto(
                adminId,
                NixRole.ADMIN,
                "admin@example.com",
                "+1234567891",
                "AdminPass123!"
        );

        assertNotNull(adminDto);
        assertThat(adminDto.id()).isEqualTo(adminId);
        assertThat(adminDto.role()).isEqualTo(NixRole.ADMIN);
        assertThat(adminDto.email()).isEqualTo("admin@example.com");
    }

    @Test
    @DisplayName("AuthUserRegisterDto should handle BUSINESS role")
    void authUserRegisterDto_ShouldHandleBusinessRole() {
        NixID businessId = NixID.of(UUID.randomUUID().toString());
        AuthUserRegisterDto businessDto = new AuthUserRegisterDto(
                businessId,
                NixRole.BUSINESS,
                "business@example.com",
                "+9876543210",
                "BusinessPass123!"
        );

        assertNotNull(businessDto);
        assertThat(businessDto.id()).isEqualTo(businessId);
        assertThat(businessDto.role()).isEqualTo(NixRole.BUSINESS);
        assertThat(businessDto.email()).isEqualTo("business@example.com");
        assertThat(businessDto.phone()).isEqualTo("+9876543210");
    }

}