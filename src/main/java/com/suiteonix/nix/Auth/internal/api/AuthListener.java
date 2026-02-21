package com.suiteonix.nix.Auth.internal.api;

import com.suiteonix.nix.Auth.internal.domain.services.AuthModule;
import com.suiteonix.nix.User.service.UserEvents;
import lombok.RequiredArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;

@Service@RequiredArgsConstructor
public class AuthListener {

    private final AuthModule authModule;

    @ApplicationModuleListener
    public void onOrgUserCreated(UserEvents.OrgUserCreated event) {
        authModule.registerOrgUserProfile(event.id(), event.orgId());
    }

}
