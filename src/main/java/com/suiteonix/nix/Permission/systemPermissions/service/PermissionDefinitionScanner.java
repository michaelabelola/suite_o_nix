package com.suiteonix.nix.Permission.systemPermissions.service;

import com.suiteonix.nix.Permission.systemPermissions.domain.DuplicatePermissionDefinitionException;

public interface PermissionDefinitionScanner {

    /**
     * Scans the given package (and sub-packages) for {@code @PermissionDefinition} /
     * {@code @PermissionDefinitions} annotations on classes, methods, and package-info files,
     * then registers all discovered definitions.
     *
     * @throws DuplicatePermissionDefinitionException if any definition id is found more than once within the scanned package,
     *                                                or already exists in the registry from a different source.
     */
    void scanAndRegisterPermissionDefinitions(String packageName) throws DuplicatePermissionDefinitionException;

    static PermissionDefinitionScanner getScanner() {
        return new PermissionDefinitionScannerImpl();
    }
}
