package com.suiteonix.nix.shared.ids;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;

@Embeddable
public record NixID(
        String id
) implements ID<String> {
    public static final NixID SYSTEM = NixID.of("SYSTEM");

    @Transient
    @Override
    public String get() {
        return id;
    }

    public static NixID of(String id) {
        return new NixID(id);
    }

    public static NixID EMPTY() {
        return NixID.of(null);
    }

}
