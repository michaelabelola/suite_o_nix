package com.suiteonix.nix.shared.ids;

import com.suiteonix.nix.shared.exceptions.EX;
import org.jspecify.annotations.NonNull;

public enum NixRole {
    USER("U"),
    CUSTOMER("C"),
    PATIENT("P"),
    ADMIN("A"),
    SUPER_ADMIN("SA"),
    ORGANIZATION("O"),
    ANONYMOUS("AN"),
    SYSTEM("S");

    private final String idValue;

    /**
     * System Role: For the service
     */
    NixRole(String idValue) {
        this.idValue = idValue;
    }

    public static @NonNull NixRole of(@NonNull NixID id) {
        return switch (id.get()) {
            case "U" -> NixRole.USER;
            case "C" -> NixRole.CUSTOMER;
            case "P" -> NixRole.PATIENT;
            case "A" -> NixRole.ADMIN;
            case "SA" -> NixRole.SUPER_ADMIN;
            case "O" -> NixRole.ORGANIZATION;
            case "S" -> NixRole.SYSTEM;
            case null, default -> NixRole.ANONYMOUS;
        };
    }


    public @NonNull NixID generateID() {
        return switch (this) {
            case SYSTEM -> NixID.of(idValue + "-" + "SYSTEM");
            case ANONYMOUS -> throw EX.badRequest("ANONYMOUS_ID_CANT_BE_GENERATED", "Anonymous ID cannot be generated");
            case SUPER_ADMIN -> NixID.of(idValue + "-" + "SUPER_ADMIN");
            default -> NixID.of(idValue + "-" + Snowflake.nextId().asString());
        };
    }
}
