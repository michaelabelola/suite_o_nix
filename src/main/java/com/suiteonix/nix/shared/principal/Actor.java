package com.suiteonix.nix.shared.principal;

import com.suiteonix.nix.kernel.security.authentication.CustomAuthentication;
import com.suiteonix.nix.shared.ids.NixID;
import com.suiteonix.nix.shared.ids.NixRole;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;
import java.util.function.Consumer;

public interface Actor extends ActiveEntity {

    static @NonNull Actor CURRENT() {
        try {
            return ((CustomAuthentication) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication())).getActor();
        } catch (Exception e) {
            return Actors.ANONYMOUS;
        }
    }

    static @NonNull NixID ID() {
        return CURRENT().id();
    }

    static @NonNull NixRole ROLE() {
        return CURRENT().role();
    }

    static Actor from(NixID actorId, NixRole actorRole) {
        return new ActorImpl(actorId, actorRole);
    }

    default void ifPresent(Consumer<Actor> supplier) {
        if (!id().isEmpty())
            supplier.accept(this);
    }

    record ActorImpl(NixID id, NixRole role) implements Actor {
    }
}
