package com.suiteonix.nix.shared.permissions.system.scanner;

import com.suiteonix.nix.shared.CustomContextHolder;
import com.suiteonix.nix.shared.permissions.system.exceptions.DuplicatePermissionDefinitionException;

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
        return CustomContextHolder.bean(PermissionDefinitionScanner.class);
    }
}
