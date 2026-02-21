package com.suiteonix.nix.Permission.internal;

import com.suiteonix.nix.Permission.PermissionId;
import com.suiteonix.nix.Common.audit.IAuditableOwnableEntity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class PermissionModel extends IAuditableOwnableEntity<PermissionModel> {

    @EmbeddedId
    PermissionId id;

    public static PermissionModel NEW() {
        var permission = new PermissionModel();
        permission.id = PermissionId.NEW();
        return permission;
    }

}
