package com.suiteonix.nix.shared.permissions.system;

import java.util.Optional;
import java.util.Set;

public interface PermissionDefinitionService {

    Set<PermissionDefinitionRecord> getAllPermissionDefinitions();

    Set<PermissionDefinitionRecord> getPermissionDefinitionByName(String name);

    Optional<PermissionDefinitionRecord> getPermissionDefinitionById(String id);

    void register(PermissionDefinitionRecord definition);

}
