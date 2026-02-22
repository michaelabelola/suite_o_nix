package com.suiteonix.nix.User.internal;

import com.suiteonix.nix.Common.audit.IAuditableOwnableEntity;
import com.suiteonix.nix.Common.audit.JpaAuditSection;
import com.suiteonix.nix.Common.ddd.AggregateRoot;
import com.suiteonix.nix.Organization.services.OrgID;
import com.suiteonix.nix.Storage.NixImage;
import com.suiteonix.nix.User.service.UserCreateDto;
import com.suiteonix.nix.User.service.UserEvents;
import com.suiteonix.nix.User.service.UserID;
import com.suiteonix.nix.shared.ids.NixID;
import com.suiteonix.nix.spi.location.HomeAddressModel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Setter(AccessLevel.PRIVATE)
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@AggregateRoot
public class UserModel extends IAuditableOwnableEntity<UserModel> {

    @EmbeddedId
    UserID id;
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

    public static UserModel NEW(UserCreateDto create) {
        UserModel user = new UserModel();
        user.id = UserID.NEW();
        user.firstname = create.firstname();
        user.lastname = create.lastname();
        user.email = create.email().toLowerCase();
        user.phone = create.phone();
        user.dateOfBirth = create.dateOfBirth();
        user.avatar = create.avatar();
        user.bio = create.bio();
        user.address = HomeAddressModel.NEW(create.address());
        user.registerEvent(UserEvents.Created.of(user.id));
        return user;
    }

    public static UserModel NEW(UserCreateDto userCreate, OrgID orgId, UserID registerer) {
        UserModel user = NEW(userCreate);
        user.setOrgID(orgId.to(NixID::NEW));
        user.setAudit(new JpaAuditSection(registerer));
        return user;
    }

    public UserModel CLONE(OrgID orgID) {
        UserModel user = new UserModel();
        user.id = UserID.NEW();
        user.firstname = firstname;
        user.lastname = getLastname();
        user.email = email;
        user.phone = phone;
        user.dateOfBirth = dateOfBirth;
        user.avatar = avatar;
        user.bio = getBio();
        user.address = getAddress();
        user.setOrgID(orgID.to(NixID::NEW));
        return user;
    }
}
