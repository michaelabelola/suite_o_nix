package com.suiteonix.nix.shared.principal;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public interface CustomAuthentication extends Authentication {
    @Override
    @Nullable Principal getPrincipal();

    @NonNull Actor getActor();
}
