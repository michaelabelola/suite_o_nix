package com.suiteonix.db.nix.User.service;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.suiteonix.db.nix.Storage.NixImage;
import com.suiteonix.db.nix.shared.audit.AuditSection;
import com.suiteonix.db.nix.shared.ids.NixID;
import com.suiteonix.db.nix.spi.location.HomeAddress;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDate;

@Schema(name = "User")
public record User(
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
        @JsonUnwrapped(prefix = "owner_")
        NixID ownerId,
        AuditSection audit
) {
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
