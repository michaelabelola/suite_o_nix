package com.suiteonix.nix.shared.permissions;

import com.suiteonix.nix.NixApplication;
import com.suiteonix.nix.Permission.systemPermissions.service.PermissionDefinitionScanner;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
@RequiredArgsConstructor
public class PermissionDefinitionConfig {

    @EventListener
    void initContextListener(ApplicationStartedEvent event) {
        PermissionDefinitionScanner.getScanner().scanAndRegisterPermissionDefinitions(NixApplication.class.getPackageName());
    }
}
