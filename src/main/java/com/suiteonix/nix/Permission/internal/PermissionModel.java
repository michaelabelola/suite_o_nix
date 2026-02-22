package com.suiteonix.nix.Permission.internal;

import com.suiteonix.nix.Common.audit.IAuditableOwnableEntity;
import com.suiteonix.nix.Common.ddd.AggregateRoot;
import com.suiteonix.nix.Permission.services.PermissionId;
import com.suiteonix.nix.shared.NixModule;
import com.suiteonix.nix.shared.ids.NixID;
import com.suiteonix.nix.shared.principal.Actor;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Table(name = "permissions")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@AggregateRoot
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder()
public class PermissionModel extends IAuditableOwnableEntity<PermissionModel> {
    @EmbeddedId
    PermissionId id;

    @Enumerated(EnumType.STRING)
    NixModule module;

    Set<String> actions;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "entity_id"))
    NixID entityId;

    //    Grantee
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "granted_to"))
    NixID granted_to;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "granted_by"))
    NixID granted_by;

    public static PermissionModel NEW() {
        var permission = new PermissionModel();
        permission.id = PermissionId.NEW();
        return permission;
    }

    public static PermissionModel GRANT_SYSTEM_PERMISSION(
            NixID grantee,
            Set<String> actions
    ) {
        var permission = new PermissionModel();
        permission.id = PermissionId.NEW();
        permission.actions = actions;
        permission.granted_to = grantee;
        permission.granted_by = Actor.ID();
        return permission;
    }

}
