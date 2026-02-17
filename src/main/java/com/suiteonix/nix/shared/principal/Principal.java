package com.suiteonix.db.nix.shared.principal;

import com.suiteonix.db.nix.kernel.security.authentication.CustomAuthentication;
import com.suiteonix.db.nix.shared.ids.NixID;
import com.suiteonix.db.nix.shared.ids.NixRole;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public interface Principal extends ActiveEntity {

    static @NonNull Principal CURRENT() {
        try {
            return Objects.requireNonNull(((CustomAuthentication) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication())).getPrincipal());
        } catch (Exception e) {
            return Principals.ANONYMOUS();
        }
    }

    static @NonNull NixID ID() {
        return CURRENT().id();
    }

    static @NonNull NixRole ROLE() {
        return CURRENT().role();
    }

    static Principal from(NixID actorId, NixRole actorRole) {
        return new PrincipalImpl(actorId, actorRole);
    }

    record PrincipalImpl(NixID id, NixRole role) implements Principal {
    }
}
