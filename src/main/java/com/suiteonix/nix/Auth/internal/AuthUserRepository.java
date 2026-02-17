package com.suiteonix.nix.Auth.internal;

import com.suiteonix.nix.shared.ValueObjects.Email;
import com.suiteonix.nix.shared.ValueObjects.Phone;
import com.suiteonix.nix.shared.ids.NixID;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUserModel, NixID> {
    Optional<AuthUserModel> findByEmailAndOwnerId(Email email, NixID ownerId);

    Optional<AuthUserModel> findByPhoneAndOwnerId(Phone phone, NixID ownerId);
}
