package com.suiteonix.nix.Permission.internal;

import com.suiteonix.nix.shared.permissions.system.PermissionDefinitionRecord;
import com.suiteonix.nix.shared.permissions.system.PermissionDefinitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class SystemPermissionAggregate {

    private final PermissionDefinitionService permissionDefinitionService;

    public Set<PermissionDefinitionRecord> getAllSystemPermissionDefinitions() {
        return permissionDefinitionService.getAllPermissionDefinitions();
    }

}
