package com.suiteonix.nix.shared.principal;

import com.suiteonix.nix.shared.ids.NixID;
import com.suiteonix.nix.shared.ids.NixRole;
import org.jspecify.annotations.NonNull;

public class Principals {

    public static final Principal SYSTEM = new Principal() {
        @Override
        public @NonNull NixID id() {
            return NixID.SYSTEM;
        }

        @Override
        public @NonNull NixRole role() {
            return NixRole.SYSTEM;
        }
    };
    public static final Principal ANONYMOUS = new Principal() {
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
