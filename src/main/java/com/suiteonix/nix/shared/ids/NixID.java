package com.suiteonix.nix.shared.ids;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;

@Embeddable
@Schema(example = "677890782656598016", type = "string")
public record NixID(
        String id
) implements ID<NixID, String> {


    public static final NixID SYSTEM = NixID.of("SYSTEM");

    public static NixID NEW() {
        return NixID.of(Snowflake.nextId().asString());
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

}
