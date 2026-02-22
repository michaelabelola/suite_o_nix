package com.suiteonix.nix.shared.ids;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;

import java.util.function.Function;

@Embeddable
@Schema(example = "677890782656598016", type = "number")
public interface NixID extends ID<NixID, Long> {
    Long value();

    NixID SYSTEM = NixID.of((long) NixRole.SYSTEM.idValue());

    static NixIDImpl NEW(NixRole role) {
        return role.generateID();
    }

    static NixIDImpl NewForRole(NixRole role) {
        return role.generateID();
    }

    default NixRole role() {
        return NixRole.of(this);
    }

    @Transient
    @Override
    default Long get() {
        return value();
    }

    static NixIDImpl of(Long id) {
        if (id == null) return EMPTY();
        return new NixIDImpl(id);
    }

    static NixIDImpl NEW(NixID id) {
        if (id == null) return EMPTY();
        return new NixIDImpl(id.get());
    }

    @Transient
    public static NixIDImpl EMPTY() {
        return NixID.of((long) NixRole.ANONYMOUS.idValue());
    }

    @Schema(hidden = true)
    @Transient
    default boolean equalsTo(NixID id) {
        if (id == null) return false;
        return this.value().equals(id.value());
    }


    default <N extends NixID> N to(Function<NixID, N> factory) {
        return factory.apply(this);
    }
}
