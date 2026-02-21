package com.suiteonix.nix.shared.ids;

import org.jspecify.annotations.NonNull;

public enum NixRole {
    SYSTEM((byte) 0),
    SUPER_ADMIN((byte) 1),
    ADMIN((byte) 2),
    ORGANIZATION((byte) 3),
    USER((byte) 4),
    CUSTOMER((byte) 5),
    ANONYMOUS((byte) 6);

    private final byte idValue;

    /**
     * System Role: For the service
     */
    NixRole(byte idValue) {
        this.idValue = idValue;
    }

    public byte idValue() {
        return idValue;
    }

    public static @NonNull NixRole of(@NonNull NixID id) {
        if (id.isEmpty()) return NixRole.ANONYMOUS;
        return switch ((int) (id.get() & 0xFF)) {
            case 0 -> NixRole.SYSTEM;
            case 1 -> NixRole.SUPER_ADMIN;
            case 2 -> NixRole.ADMIN;
            case 3 -> NixRole.ORGANIZATION;
            case 4 -> NixRole.USER;
            case 5 -> NixRole.CUSTOMER;
            default -> NixRole.ANONYMOUS;
        };
    }
    public @NonNull NixID generateID() {
        var id = String.valueOf(Snowflake.nextId().id()) + idValue();
        return NixID.of(Long.valueOf(id));
    }
}
