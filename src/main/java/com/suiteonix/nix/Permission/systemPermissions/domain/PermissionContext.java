package com.suiteonix.nix.Permission.systemPermissions.domain;

import com.suiteonix.nix.shared.permissions.system.PermissionDefinitionRecord;

import java.util.Optional;

public final class PermissionContext {

    private static final ThreadLocal<PermissionDefinitionRecord> HOLDER = new ThreadLocal<>();

    private PermissionContext() {}

    public static void set(PermissionDefinitionRecord definition) {
        HOLDER.set(definition);
    }

    public static Optional<PermissionDefinitionRecord> get() {
        return Optional.ofNullable(HOLDER.get());
    }

    public static void clear() {
        HOLDER.remove();
    }
}
