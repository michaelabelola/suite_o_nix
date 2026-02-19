package com.suiteonix.nix.Permission.systemPermissions.service;

import com.suiteonix.nix.Permission.systemPermissions.domain.PermissionDefinitionRecord;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

class Definitions {
    static final Set<PermissionDefinitionRecord> definitionRecords = ConcurrentHashMap.newKeySet();

    static void register(PermissionDefinitionRecord definition) {
        definitionRecords.add(definition);
    }

    static Set<PermissionDefinitionRecord> getAll() {
        return Collections.unmodifiableSet(Set.copyOf(definitionRecords));
    }

    static Optional<PermissionDefinitionRecord> getById(String id) {
        return Definitions.getAll()
                .stream()
                .filter(permissionDefinitionRecord -> permissionDefinitionRecord.id().equals(id))
                .findFirst();
    }
}
