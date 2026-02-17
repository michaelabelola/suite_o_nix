package com.suiteonix.db.nix.kernel.security.authentication;

import com.suiteonix.db.nix.shared.principal.Actor;
import com.suiteonix.db.nix.shared.principal.Actors;
import com.suiteonix.db.nix.shared.principal.Principal;
import com.suiteonix.db.nix.shared.principal.Principals;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;

public interface CustomAuthentication extends Authentication {
    static CustomAuthentication of(Principal principal, Actor actor, Set<? extends GrantedAuthority> authorities) {
        return new CustomAuthenticationImpl(principal, actor, authorities);
    }

    static CustomAuthentication ofAnonymous() {
        return new CustomAuthenticationImpl(Principals.ANONYMOUS(), Actors.ANONYMOUS(), new HashSet<>());
    }

    static CustomAuthentication ofSystem() {
        return new CustomAuthenticationImpl(Principals.SYSTEM(), Actors.SYSTEM(), new HashSet<>());
    }

    @Override
    @Nullable Principal getPrincipal();

    @NonNull Actor getActor();
}
