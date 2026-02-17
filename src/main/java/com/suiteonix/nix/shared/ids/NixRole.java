package com.suiteonix.nix.shared.ids;

import com.suiteonix.nix.shared.exceptions.EX;

public enum NixRole {
    USER("U"),
    CUSTOMER("C"),
    PATIENT("P"),
    ADMIN("A"),
    SUPER_ADMIN("SA"),
    BUSINESS("B"),
    ANONYMOUS("AN"),
    SYSTEM("S");

    private final String idValue;

    /**
     * System Role: For the service
     */
    NixRole(String idValue) {
        this.idValue = idValue;
    }


    public NixID generateID() {
        return switch (this) {
            case SYSTEM -> NixID.of(idValue + "-" + "SYSTEM");
            case ANONYMOUS -> throw EX.badRequest("ANONYMOUS_ID_CANT_BE_GENERATED", "Anonymous ID cannot be generated");
            case SUPER_ADMIN -> NixID.of(idValue + "-" + "SUPER_ADMIN");
            default -> NixID.of(idValue + "-" + Snowflake.nextId().asString());
        };
    }
}
