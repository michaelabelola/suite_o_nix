package com.suiteonix.nix.Permission.api;

import com.suiteonix.nix.Permission.internal.SystemPermissionAggregate;
import com.suiteonix.nix.shared.permissions.system.PermissionDefinitionRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RequestMapping
@RestController
@RequiredArgsConstructor
class SystemPermissionController {

    private final SystemPermissionAggregate systemPermissionAggregate;

//    @PreAuthorize("T(com.suiteonix.nix.shared.principal.Actor).ROLE().equals(T(com.suiteonix.nix.shared.ids.NixRole).ADMIN)")
    @GetMapping("permissions/system-permissions")
    Set<PermissionDefinitionRecord> getAllPermissionDefinitions() {
        return systemPermissionAggregate.getAllSystemPermissionDefinitions();
    }

}
