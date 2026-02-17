package com.suiteonix.db.nix.Auth.internal;

import com.suiteonix.db.nix.shared.ids.NixID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthUserRepository extends JpaRepository<AuthUserModel, NixID> {
}
