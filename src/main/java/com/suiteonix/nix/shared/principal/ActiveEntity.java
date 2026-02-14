package com.suiteonix.nix.shared.principal;

import com.suiteonix.nix.shared.ids.NixID;
import com.suiteonix.nix.shared.ids.NixRole;
import com.suiteonix.nix.shared.interfaces.EmptyChecker;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Transient;
import org.jspecify.annotations.NonNull;

public interface ActiveEntity extends EmptyChecker {
    @NonNull NixID id();

    @NonNull NixRole role();

    @Override
    default boolean isEmpty() {
        return id().isEmpty();
    }


    @Schema(hidden = true)
    @Transient
    default boolean equalsTo(ActiveEntity activeEntity) {
        if (activeEntity == null) return false;
        return id().equals(activeEntity.id()) && role().equals(activeEntity.role());
    }
}
