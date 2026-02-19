package com.suiteonix.nix.User.service;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.suiteonix.nix.Storage.NixImage;
import com.suiteonix.nix.shared.audit.AuditSection;
import com.suiteonix.nix.shared.ids.NixID;
import com.suiteonix.nix.spi.location.HomeAddress;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDate;

@Schema(name = "User")
public record User(
        @JsonUnwrapped
        NixID id,
        String firstname,
        String lastname,
        @JsonUnwrapped(prefix = "avatar_")
        NixImage avatar,
        @JsonUnwrapped(prefix = "org_")
        NixID orgID
) {
    @Schema(name = "User.Detailed")
    public record Detailed(
            @JsonUnwrapped
            NixID id,
            String firstname,
            String lastname,
            String email,
            String phone,
            LocalDate dateOfBirth,
            @JsonUnwrapped(prefix = "avatar_")
            NixImage avatar,
            String bio,
            HomeAddress address,
            @JsonUnwrapped(prefix = "org_")
            NixID orgID,
            AuditSection audit
    ) {

    }
    @Builder
    @Schema(name = "User.Create")
    public record Create(
            String firstname,
            String lastname,
            String email,
            String phone,
            LocalDate dateOfBirth,
            NixImage avatar,
            String bio,
            HomeAddress.Create address
    ) {
    }
}
