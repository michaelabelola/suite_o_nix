package com.suiteonix.nix.Permission.internal;

import com.suiteonix.nix.Common.audit.IAuditableOwnableEntity;
import com.suiteonix.nix.Permission.PermissionId;
import com.suiteonix.nix.shared.NixModule;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class PermissionModel extends IAuditableOwnableEntity<PermissionModel> {

    @EmbeddedId
    PermissionId id;
    String systemPermissionId;
    NixModule module;
    List<String> actions;


    public static PermissionModel NEW() {
        var permission = new PermissionModel();
        permission.id = PermissionId.NEW();
        return permission;
    }

}
