package com.suiteonix.nix.shared.ids;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.jspecify.annotations.NonNull;

import java.util.Objects;

@Embeddable
@Schema(example = "677890782656598016", type = "number")
public record NixIDImpl(
        @Column(name = "id")
        Long value
) implements NixID {

    @Override
    public @NonNull String toString() {
        return String.valueOf(value());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NixID nixID)
            return Objects.equals(value, nixID.get());
        return false;
    }
}
