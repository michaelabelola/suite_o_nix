package com.suiteonix.nix.Auth.internal;

import com.suiteonix.nix.shared.ids.NixID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthUserRepository extends JpaRepository<AuthUserModel, NixID> {
}
