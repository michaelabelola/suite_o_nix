package com.suiteonix.nix.Permission.systemPermissions;

import com.suiteonix.nix.shared.permissions.system.exceptions.DuplicatePermissionDefinitionException;
import com.suiteonix.nix.shared.permissions.system.PermissionDefinitionRecord;
import com.suiteonix.nix.shared.permissions.system.PermissionDefinitionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
class PermissionDefinitionServiceImpl implements PermissionDefinitionService {

    @Override
    public Set<PermissionDefinitionRecord> getAllPermissionDefinitions() {
        return Definitions.getAll();
    }

    @Override
    public Set<PermissionDefinitionRecord> getPermissionDefinitionByName(String name) {
        return Definitions.getAll()
                .stream()
                .filter(permissionDefinitionRecord -> permissionDefinitionRecord.name().equals(name))
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<PermissionDefinitionRecord> getPermissionDefinitionById(String id) {
        return Definitions.getAll()
                .stream()
                .filter(permissionDefinitionRecord -> permissionDefinitionRecord.id().equals(id))
                .findFirst();
    }

    @Override
    public void register(PermissionDefinitionRecord definition) {

        getPermissionDefinitionById(definition.id()).ifPresent(existing -> {
            throw new DuplicatePermissionDefinitionException(Map.of(
                    definition.id(), List.of(existing.sourceLocation(), definition.sourceLocation())
            ));
        });
        Definitions.register(definition);
    }
}
