package com.suiteonix.db.nix.kernel.security.authentication;

import com.suiteonix.db.nix.shared.principal.Actor;
import com.suiteonix.db.nix.shared.principal.Principal;
import com.suiteonix.db.nix.shared.principal.Principals;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;

class CustomAuthenticationImpl implements CustomAuthentication {

    @NonNull
    private final Principal principal;
    @NonNull
    private final Actor actor;
    private boolean authenticated = false;
    @NonNull
    Set<? extends GrantedAuthority> authorities = new HashSet<>();

    CustomAuthenticationImpl(@NonNull Principal principal, @NonNull Actor actor, @Nullable Set<? extends GrantedAuthority> authorities) {
        this.principal = principal;
        this.actor = actor;
        if (!actor.isEmpty() && !Principals.ANONYMOUS().equalsTo(actor))
            setAuthenticated(true);
        if (authorities != null)
            this.authorities = authorities;
    }

    @Override
    public @NonNull Set<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public @Nullable Object getCredentials() {
        return null;
    }

    @Override
    public @Nullable Object getDetails() {
        return null;
    }

    @Override
    public @NonNull Principal getPrincipal() {
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public @NonNull Actor getActor() {
        return actor;
    }

    @Override
    public String getName() {
        return principal.id().get();
    }
}
