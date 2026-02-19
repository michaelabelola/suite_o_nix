package com.suiteonix.nix.Auth.internal.infrastructure;

import com.suiteonix.nix.Auth.internal.domain.AuthProfileModel;
import com.suiteonix.nix.shared.ValueObjects.Email;
import com.suiteonix.nix.shared.ValueObjects.Phone;
import com.suiteonix.nix.shared.ids.NixID;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthProfileModel, NixID> {
    Optional<AuthProfileModel> findByEmailAndOrgID(Email email, NixID orgID);

    Optional<AuthProfileModel> findByPhoneAndOrgID(Phone phone, NixID orgID);

    Optional<AuthProfileModel> findByEmail(Email email);
}
