package com.suiteonix.nix.Permission.internal;

import com.suiteonix.nix.shared.permissions.system.PermissionDefinitionRecord;
import com.suiteonix.nix.shared.permissions.system.PermissionDefinitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class SystemPermissionAggregate {

    private final PermissionDefinitionService permissionDefinitionService;

    public Set<PermissionDefinitionRecord> getAllSystemPermissionDefinitions() {
        return permissionDefinitionService.getAllPermissionDefinitions();
    }

    @RequestMapping
    @RestController("SystemPermissionController")
    @RequiredArgsConstructor
    static class Controller {

        private final SystemPermissionAggregate systemPermissionAggregate;

    //    @PreAuthorize("T(com.suiteonix.nix.shared.principal.Actor).ROLE().equals(T(com.suiteonix.nix.shared.ids.NixRole).ADMIN)")
        @GetMapping("permissions/system-permissions")
        Set<PermissionDefinitionRecord> getAllPermissionDefinitions() {
            return systemPermissionAggregate.getAllSystemPermissionDefinitions();
        }
    }
}
