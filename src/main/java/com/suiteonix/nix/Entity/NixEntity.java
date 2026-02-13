package com.suiteonix.nix.Entity;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.suiteonix.nix.shared.audit.AuditSection;
import com.suiteonix.nix.shared.ids.NixID;
import com.suiteonix.nix.shared.ids.NixRole;

public record NixEntity(
        @JsonUnwrapped
        NixID id,
        String name,
        String email,
        String phone,
        String shortName,
        NixRole role,
        String country,
        String bio,
        @JsonUnwrapped(prefix = "owner_")
        NixID ownerId,
        AuditSection audit
) {
    public static NixEntity of(
            NixID id,
            String name,
            String email,
            String phone,
            String shortName,
            NixRole role,
            String country,
            String bio,
            NixID ownerId,
            AuditSection audit
    ) {
        return new NixEntity(id, name, email, phone, shortName, role, country, bio, ownerId, audit);
    }
}
