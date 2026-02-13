package com.suiteonix.nix.shared.principal;

import com.suiteonix.nix.shared.ids.NixID;
import com.suiteonix.nix.shared.ids.NixRole;
import org.jspecify.annotations.NonNull;

public interface ActiveEntity {
    @NonNull NixID id();

    @NonNull NixRole role();
}
