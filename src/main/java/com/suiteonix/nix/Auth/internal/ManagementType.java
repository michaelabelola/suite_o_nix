package com.suiteonix.nix.Auth.internal;

/**
 * Defines the management strategies for authentication and authorization.
 */
public enum ManagementType {
    /**
     * Managed by the user themselves.
     */
    SELF,

    /**
     * Managed by the owner of the resource or organization.
     */
    OWNER,

    /**
     * Jointly managed by both the user and the owner.
     */
    SELF_OWNER,
}
