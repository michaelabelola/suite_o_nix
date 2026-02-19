package com.suiteonix.nix.Permission.systemPermissions.service;

import com.suiteonix.nix.Permission.systemPermissions.domain.PermissionDefinitionRecord;

import java.util.Optional;
import java.util.Set;

public interface PermissionDefinitionService {

    Set<PermissionDefinitionRecord> getAllPermissionDefinitions();

    Set<PermissionDefinitionRecord> getPermissionDefinitionByName(String name);

    Optional<PermissionDefinitionRecord> getPermissionDefinitionById(String id);

    void register(PermissionDefinitionRecord definition);

}
