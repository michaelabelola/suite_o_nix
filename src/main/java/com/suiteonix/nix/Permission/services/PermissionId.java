package com.suiteonix.nix.Permission.services;

import com.suiteonix.nix.shared.ids.ID;
import com.suiteonix.nix.shared.ids.Snowflake;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record PermissionId(
        @Column(name = "id")
        String value
) implements ID<PermissionId, String> {

    public static PermissionId NEW() {
        return new PermissionId(Snowflake.nextId().asString());
    }

    @Override
    public String get() {
        return value;
    }
}
