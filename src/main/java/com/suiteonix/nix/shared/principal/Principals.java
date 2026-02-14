package com.suiteonix.nix.shared.principal;

import com.suiteonix.nix.shared.ids.NixID;
import com.suiteonix.nix.shared.ids.NixRole;
import org.jspecify.annotations.NonNull;

public class Principals {

    public static Principal SYSTEM() {
        return new Principal() {
            @Override
            public @NonNull NixID id() {
                return NixID.SYSTEM;
            }

            @Override
            public @NonNull NixRole role() {
                return NixRole.SYSTEM;
            }
        };
    }

    public static Principal ANONYMOUS() {
        return new Principal() {
            @Override
            public @NonNull NixID id() {
                return NixID.EMPTY();
            }

            @Override
            public @NonNull NixRole role() {
                return NixRole.ANONYMOUS;
            }
        };
    }
}
