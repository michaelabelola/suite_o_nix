package com.suiteonix.nix.shared.ids;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import org.jspecify.annotations.NonNull;

import java.util.Objects;

@Embeddable
@Schema(example = "677890782656598016", type = "number")
public record NixID(
        Long id
) implements ID<NixID, Long> {

    public static final NixID SYSTEM = NixID.of((long) NixRole.SYSTEM.idValue());

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
    public Long get() {
        return id;
    }

    public static NixID of(Long id) {
        if (id == null) return EMPTY();
        return new NixID(id);
    }

    @Transient
    public static NixID EMPTY() {
        return NixID.of((long) NixRole.ANONYMOUS.idValue());
    }

    @Schema(hidden = true)
    @Transient
    public boolean equalsTo(NixID id) {
        if (id == null) return false;
        return this.id.equals(id.id);

    }

    @Override
    public @NonNull String toString() {
        return String.valueOf(id());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NixID(Long id1))
            return Objects.equals(id1, id);
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
