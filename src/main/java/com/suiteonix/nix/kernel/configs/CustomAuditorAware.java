package com.suiteonix.nix.kernel.configs;

import com.suiteonix.nix.shared.ids.NixID;
import com.suiteonix.nix.shared.principal.Actor;
import com.suiteonix.nix.shared.principal.Actors;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
class CustomAuditorAware implements AuditorAware<NixID> {

    @NotNull
    @Override
    public Optional<NixID> getCurrentAuditor() {
        if (SecurityContextHolder.getContext().getAuthentication() == null || SecurityContextHolder.getContext().getAuthentication().getPrincipal() == null)
            return Optional.of(Actors.SYSTEM().id());
        try {
            return Optional.of(Actor.CURRENT().id());
        } catch (Exception e) {
            return Optional.of(Actors.SYSTEM().id());
        }
    }
}