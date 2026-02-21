package com.suiteonix.nix.User.internal;

import com.suiteonix.nix.Organization.services.OrgEvents;
import lombok.RequiredArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserEventListener {

    private final UserModule userModule;

    @ApplicationModuleListener
    public void organizationCreatedEvent(OrgEvents.Created event) {
        userModule.registerDefaultOrgUser(event.orgId(), event.registerer());
    }
}
