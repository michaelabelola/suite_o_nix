package com.suiteonix.nix.shared.principal;

import com.suiteonix.nix.shared.ids.NixID;
import com.suiteonix.nix.shared.ids.NixRole;
import com.suiteonix.nix.shared.interfaces.EmptyChecker;
import org.jspecify.annotations.NonNull;

public interface ActiveEntity extends EmptyChecker {
    @NonNull NixID id();

    @NonNull NixRole role();

    @Override
    default boolean isEmpty() {
        return id().isEmpty();
    }
}
