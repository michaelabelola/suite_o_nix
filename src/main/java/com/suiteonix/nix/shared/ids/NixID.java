package com.suiteonix.nix.shared.ids;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import org.jspecify.annotations.NonNull;

import java.util.Objects;

@Embeddable
@Schema(example = "U-677890782656598016", type = "string")
public record NixID(
        String id
) implements ID<NixID, String> {


    public static final NixID SYSTEM = NixRole.SYSTEM.generateID();

    public static NixID NEW(NixRole role) {
        return role.generateID();
    }

    public static NixID NewForRole(NixRole role) {
        return role.generateID();
    }

    public NixRole role() {
       return NixRole.of(this);
    }

    @Transient
    @Override
    public String get() {
        return id;
    }

    public static NixID of(String id) {
        return new NixID(id);
    }

    @Transient
    public static NixID EMPTY() {
        return NixID.of(null);
    }

    @Schema(hidden = true)
    @Transient
    public boolean equalsTo(NixID id) {
        if (id == null) return false;
        return this.id.equals(id.id);

    }

    @Override
    public @NonNull String toString() {
        return id();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NixID(String id1))
            return Objects.equals(id1, id);
        return false;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id);
    }
}
