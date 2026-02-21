package com.suiteonix.nix.shared.permissions.system;

import com.suiteonix.nix.shared.permissions.system.annotations.PermissionDefinition;
import com.suiteonix.nix.shared.NixModule;
import com.suiteonix.nix.shared.ids.NixRole;

import java.util.Arrays;
import java.util.List;

public record PermissionDefinitionRecord(
        String id,
        String name,
        List<NixRole> roles,
        NixModule module,
        List<String> actions,
        String sourceLocation
) {
    public static PermissionDefinitionRecord from(PermissionDefinition annotation, String sourceLocation) {
        return new PermissionDefinitionRecord(
                annotation.id(),
                annotation.name(),
                Arrays.asList(annotation.roles()),
                annotation.module(),
                Arrays.asList(annotation.action()),
                sourceLocation
        );
    }
}
