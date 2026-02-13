package com.suiteonix.nix.shared.principal;

import com.suiteonix.nix.shared.ids.NixID;
import com.suiteonix.nix.shared.ids.NixRole;
import org.jspecify.annotations.NonNull;

public class Actors {

    public static final Actor SYSTEM = new Actor() {
        @Override
        public @NonNull NixID id() {
            return NixID.SYSTEM;
        }

        @Override
        public @NonNull NixRole role() {
            return NixRole.SYSTEM;
        }
    };
    public static final Actor ANONYMOUS = new Actor() {
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
