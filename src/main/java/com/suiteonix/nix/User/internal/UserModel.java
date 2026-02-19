package com.suiteonix.nix.User.internal;

import com.suiteonix.nix.Storage.NixImage;
import com.suiteonix.nix.shared.audit.IAuditableOwnableEntity;
import com.suiteonix.nix.shared.audit.JpaAuditSection;
import com.suiteonix.nix.shared.ddd.AggregateRoot;
import com.suiteonix.nix.shared.ids.NixID;
import com.suiteonix.nix.shared.ids.NixRole;
import com.suiteonix.nix.User.service.User;
import com.suiteonix.nix.spi.location.HomeAddressModel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@AggregateRoot
public class UserModel extends IAuditableOwnableEntity<UserModel> {

    @EmbeddedId
    NixID id;
    String firstname;
    String lastname;
    String email;
    String phone;
    LocalDate dateOfBirth;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "url", column = @Column(name = "avatar", columnDefinition = "TEXT"))
    })
    NixImage avatar = NixImage.EMPTY();

    @Column(name = "bio", columnDefinition = "TEXT")
    String bio;

    @Embedded
    HomeAddressModel address;

    @Embedded
    @Setter
    JpaAuditSection audit;

    public static UserModel NEW(User.Create create) {
        UserModel user = new UserModel();
        user.id = NixID.NewForRole(NixRole.USER);
        user.firstname = create.firstname();
        user.lastname = create.lastname();
        user.email = create.email().toLowerCase();
        user.phone = create.phone();
        user.dateOfBirth = create.dateOfBirth();
        user.avatar = create.avatar();
        user.bio = create.bio();
        user.address = HomeAddressModel.NEW(create.address());
        return user;
    }
}
