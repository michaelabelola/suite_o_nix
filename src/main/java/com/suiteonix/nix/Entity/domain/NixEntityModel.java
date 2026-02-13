package com.suiteonix.nix.Entity.domain;

import com.suiteonix.nix.Entity.NixEntity;
import com.suiteonix.nix.shared.audit.IAuditableOwnableEntity;
import com.suiteonix.nix.shared.audit.JpaAuditSection;
import com.suiteonix.nix.shared.ids.NixID;
import com.suiteonix.nix.shared.ids.NixRole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "nix_entity")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class NixEntityModel extends IAuditableOwnableEntity<NixEntityModel, NixEntity> {

    @EmbeddedId
    NixID id;

    @Column(nullable = false)
    String name;

    String email;

    String phone;

    String shortName;

    @Column(columnDefinition = "VARCHAR", length = 225)
    @Enumerated(EnumType.STRING)
    NixRole role;

    @Column(columnDefinition = "VARCHAR", length = 225, nullable = false)
    String country;

    String bio;

//    @Embedded
//    @AttributeOverride(name = "url", column = @Column(name = "avatar", columnDefinition = "TEXT"))
//    NixImage avatar;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "owner_id", updatable = false))
    NixID ownerId;

    @Embedded
    JpaAuditSection audit;

}
