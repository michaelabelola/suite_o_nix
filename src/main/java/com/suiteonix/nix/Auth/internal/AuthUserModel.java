package com.suiteonix.nix.Auth.internal;

import com.suiteonix.nix.Auth.AuthUser;
import com.suiteonix.nix.shared.ValueObjects.Email;
import com.suiteonix.nix.shared.ValueObjects.Password;
import com.suiteonix.nix.shared.ValueObjects.Phone;
import com.suiteonix.nix.shared.audit.IAuditableOwnableEntity;
import com.suiteonix.nix.shared.ddd.AggregateRoot;
import com.suiteonix.nix.shared.ids.NixID;
import com.suiteonix.nix.shared.ids.NixRole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@AggregateRoot
class AuthUserModel extends IAuditableOwnableEntity<AuthUserModel, AuthUser> {

    @EmbeddedId
    NixID id;

    @Enumerated(EnumType.STRING)
    NixRole role;

    @Embedded
    Email email;

    @Embedded
    Phone phone;

    @Embedded
    Password password;

    public static AuthUserModel NEW(
            NixID nixID,
            NixRole role,
            Email email,
            Phone phone,
            Password password
    ) {
        return new AuthUserModel(
                nixID,
                role,
                email,
                phone,
                password
        );
    }
}
